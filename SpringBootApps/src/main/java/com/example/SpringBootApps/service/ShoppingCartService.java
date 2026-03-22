package com.example.SpringBootApps.service;

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

        // Fetch price
        Double bookPrice = jdbcTemplate.queryForObject(
                "SELECT price FROM book_table WHERE id = ?", Double.class, bookId);

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + 1;
            existingItem.setQuantity(newQuantity);
            existingItem.setTotalPrice(newQuantity * bookPrice);
            return shoppingCartRepository.save(existingItem);
        }

        ShoppingCart cartItem = new ShoppingCart();
        cartItem.setUserId(userId);
        cartItem.setBookId(bookId);
        cartItem.setQuantity(1);
        cartItem.setTotalPrice(bookPrice);

        return shoppingCartRepository.save(cartItem);
    }

    // Remove book
    @Transactional
    public void removeBookFromCart(Integer userId, Long bookId, Integer quantity) {
        
        ShoppingCart item = shoppingCartRepository.findByUserIdAndBookId(userId, bookId);

        if (item == null) {
            throw new RuntimeException("Item not found in cart");
        }
        int currentQuantity = item.getQuantity();

        // Case 1: Remove completely
        if (quantity >= currentQuantity) {
            shoppingCartRepository.delete(item);
            return;
        }

        // Case 2: Reduce quantity
        int newQuantity = currentQuantity - quantity;

        // Get unit price
        double unitPrice = item.getTotalPrice() / currentQuantity;

        item.setQuantity(newQuantity);
        item.setTotalPrice(
            BigDecimal.valueOf(unitPrice * newQuantity)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue()
        );

        shoppingCartRepository.save(item);
    }

    // Get cart with full book info
    public List<Map<String, Object>> getUserCartWithBooks(Integer userId) {
        List<ShoppingCart> cartItems = shoppingCartRepository.findByUserId(userId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (ShoppingCart item : cartItems) {
            // Fetch full book info as Map
            String sql = "SELECT * FROM book_table WHERE id = ?";
            Map<String, Object> bookMap = jdbcTemplate.queryForMap(sql, item.getBookId());

            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("userId", item.getUserId());
            map.put("quantity", item.getQuantity());
            map.put("totalPrice", item.getTotalPrice());
            map.put("book", bookMap);

            result.add(map);
        }
        return result;
    }

    // Get subtotal
    public Double getCartSubtotal(Integer userId) {
        List<ShoppingCart> cartItems = shoppingCartRepository.findByUserId(userId);
        double subtotal = 0.0;
        for (ShoppingCart item : cartItems) {
            Double price = jdbcTemplate.queryForObject(
                    "SELECT price FROM book_table WHERE id = ?", Double.class, item.getBookId());
            subtotal += price * item.getQuantity();
        }
        return subtotal;
    }
}