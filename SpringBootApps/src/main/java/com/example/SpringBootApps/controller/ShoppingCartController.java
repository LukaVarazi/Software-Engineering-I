package com.example.SpringBootApps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootApps.entity.Book;
import com.example.SpringBootApps.entity.ShoppingCart;
import com.example.SpringBootApps.repository.BookRepository;
import com.example.SpringBootApps.repository.ShoppingCartRepository;

@RestController
@RequestMapping("")
public class ShoppingCartController {

    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ShoppingCartController(
            ShoppingCartRepository shoppingCartRepository,
            BookRepository bookRepository) {

        this.shoppingCartRepository = shoppingCartRepository;
        this.bookRepository = bookRepository;
    }

    // STORY #16
    // Retrieve all books in user's cart
    @GetMapping("/cart/{userId}")
    public List<ShoppingCart> getUserCart(@PathVariable Integer userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    // STORY #16
    // Calculate subtotal
    @GetMapping("/cart/{userId}/subtotal")
    public Double getCartSubtotal(@PathVariable Integer userId) {

        List<ShoppingCart> cartItems = shoppingCartRepository.findByUserId(userId);

        double subtotal = 0;

        for (ShoppingCart item : cartItems) {
            subtotal += item.getTotalPrice();
        }

        return subtotal;
    }

    // STORY #17
    // Add book to cart
    @PostMapping("/cart/add")
    public ShoppingCart addBookToCart(
            @RequestParam Integer userId,
            @RequestParam Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // check if already in cart
        ShoppingCart existingItem =
                shoppingCartRepository.findByUserIdAndBook_Id(userId, bookId);

        if (existingItem != null) {

            existingItem.setQuantity(existingItem.getQuantity() + 1);

            existingItem.setTotalPrice(
                    existingItem.getQuantity() * book.getPrice()
            );

            return shoppingCartRepository.save(existingItem);
        }

        ShoppingCart cartItem = new ShoppingCart();

        cartItem.setUserId(userId);
        cartItem.setBook(book);
        cartItem.setQuantity(1);
        cartItem.setTotalPrice(book.getPrice());

        return shoppingCartRepository.save(cartItem);
    }

    // STORY #17
    // Remove book from cart
    @DeleteMapping("/cart/remove")
    public void removeBookFromCart(
            @RequestParam Integer userId,
            @RequestParam Long bookId) {

        shoppingCartRepository.deleteByUserIdAndBook_Id(userId, bookId);
    }
}