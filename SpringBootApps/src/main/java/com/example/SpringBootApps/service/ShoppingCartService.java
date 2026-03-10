package com.example.SpringBootApps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootApps.entity.Book;
import com.example.SpringBootApps.entity.ShoppingCart;
import com.example.SpringBootApps.repository.BookRepository;
import com.example.SpringBootApps.repository.ShoppingCartRepository;

import jakarta.transaction.Transactional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               BookRepository bookRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.bookRepository = bookRepository;
    }

    // Retrieve all books in user's cart
    public List<ShoppingCart> getUserCart(Integer userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    // Calculate subtotal
    public Double getCartSubtotal(Integer userId) {

        List<ShoppingCart> cartItems = shoppingCartRepository.findByUserId(userId);

        double subtotal = 0.0;

        for (ShoppingCart item : cartItems) {
            subtotal += item.getTotalPrice();
        }

        return subtotal;
    }

    // Add book to cart
    public ShoppingCart addBookToCart(Integer userId, Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        ShoppingCart existingItem =
                shoppingCartRepository.findByUserIdAndBook_Id(userId, bookId);

        if (existingItem != null) {

            int newQuantity = existingItem.getQuantity() + 1;
            existingItem.setQuantity(newQuantity);

            existingItem.setTotalPrice(newQuantity * book.getPrice());

            return shoppingCartRepository.save(existingItem);
        }

        ShoppingCart newItem = new ShoppingCart();

        newItem.setUserId(userId);
        newItem.setBook(book);
        newItem.setQuantity(1);
        newItem.setTotalPrice(book.getPrice());

        return shoppingCartRepository.save(newItem);
    }

    // Remove book from cart
    @Transactional
    public void removeBookFromCart(Integer userId, Long bookId) {
        shoppingCartRepository.deleteByUserIdAndBook_Id(userId, bookId);
    }
}