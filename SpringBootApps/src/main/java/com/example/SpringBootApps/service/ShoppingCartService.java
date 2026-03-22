package com.example.SpringBootApps.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.SpringBootApps.entity.ShoppingCart;
import com.example.SpringBootApps.repository.ShoppingCartRepository;

import jakarta.transaction.Transactional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               JdbcTemplate jdbcTemplate) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Add book to cart
    public ShoppingCart addBookToCart(Integer userId, Long bookId) {
        // Check if book exists
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM book_table WHERE id = ?", Integer.class, bookId);
        if (count == null || count == 0) throw new RuntimeException("Book not found");

        ShoppingCart existingItem = shoppingCartRepository.findByUserIdAndBookId(userId, bookId);

        // Fetch price and discount
        Map<String, Object> priceData = jdbcTemplate.queryForMap(
                "SELECT price, discount_percent FROM book_table WHERE id = ?", bookId);

        Double price = ((Number) priceData.get("price")).doubleValue();
        Double discountPercent = ((Number) priceData.get("discount_percent")).doubleValue();

        // Calculate discounted price and round to 2 decimals
        Double discountedPrice = BigDecimal.valueOf(price)
                .multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(discountPercent).divide(BigDecimal.valueOf(100))))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + 1;

            existingItem.setQuantity(newQuantity);
            existingItem.setTotalPrice(BigDecimal.valueOf(discountedPrice)
                    .multiply(BigDecimal.valueOf(newQuantity))
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue());

            return shoppingCartRepository.save(existingItem);
        }

        ShoppingCart cartItem = new ShoppingCart();
        cartItem.setUserId(userId);
        cartItem.setBookId(bookId); // make sure setter accepts bookId
        cartItem.setQuantity(1);
        cartItem.setTotalPrice(discountedPrice);

        return shoppingCartRepository.save(cartItem);
    }

    // Remove book from cart
    @Transactional
    public void removeBookFromCart(Integer userId, Long bookId) {
        shoppingCartRepository.deleteByUserIdAndBookId(userId, bookId);
    }

    // Get cart with full book info
    public List<Map<String, Object>> getUserCartWithBooks(Integer userId) {
        List<ShoppingCart> cartItems = shoppingCartRepository.findByUserId(userId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (ShoppingCart item : cartItems) {
            // Fetch full book info
            String sql = "SELECT * FROM book_table WHERE id = ?";
            Map<String, Object> bookMap = jdbcTemplate.queryForMap(sql, item.getBookId());

            Double price = ((Number) bookMap.get("price")).doubleValue();
            Double discount = ((Number) bookMap.get("discount_percent")).doubleValue();

            Double discountedPrice = BigDecimal.valueOf(price)
                    .multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100))))
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();

            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("userId", item.getUserId());
            map.put("quantity", item.getQuantity());
            map.put("totalPrice", item.getTotalPrice());
            map.put("book", bookMap);
            map.put("discountedPrice", discountedPrice);

            result.add(map);
        }
        return result;
    }

    // Get subtotal with discounts applied
    public Double getCartSubtotal(Integer userId) {
        List<ShoppingCart> cartItems = shoppingCartRepository.findByUserId(userId);
        BigDecimal subtotal = BigDecimal.ZERO;

        for (ShoppingCart item : cartItems) {
            Map<String, Object> priceData = jdbcTemplate.queryForMap(
                    "SELECT price, discount_percent FROM book_table WHERE id = ?", item.getBookId());

            Double price = ((Number) priceData.get("price")).doubleValue();
            Double discountPercent = ((Number) priceData.get("discount_percent")).doubleValue();

            Double discountedPrice = BigDecimal.valueOf(price)
                    .multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(discountPercent).divide(BigDecimal.valueOf(100))))
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();

            subtotal = subtotal.add(BigDecimal.valueOf(discountedPrice * item.getQuantity()));
        }

        return subtotal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}