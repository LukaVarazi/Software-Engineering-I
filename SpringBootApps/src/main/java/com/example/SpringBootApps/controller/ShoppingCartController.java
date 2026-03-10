package com.example.SpringBootApps.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootApps.entity.ShoppingCart;
import com.example.SpringBootApps.service.ShoppingCartService;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    // Add book to cart
    @PostMapping("/add")
    public ShoppingCart addBookToCart(@RequestParam Integer userId,
                                      @RequestParam Long bookId) {
        return shoppingCartService.addBookToCart(userId, bookId);
    }

    // Remove book from cart
    @DeleteMapping("/remove")
    public Map<String, String> removeBookFromCart(@RequestParam Integer userId,
                                              @RequestParam Long bookId) {
        shoppingCartService.removeBookFromCart(userId, bookId);

        Map<String, String> response = new HashMap<>();
        response.put("Confirmation! ", "The selected book has been deleted from the cart.");
        return response;
    }

    // Get cart with full book info
    @GetMapping("/{userId}/full")
    public List<Map<String, Object>> getUserCartWithBooks(@PathVariable Integer userId) {
        return shoppingCartService.getUserCartWithBooks(userId);
    }

    // Get subtotal
    @GetMapping("/{userId}/subtotal")
    public Double getCartSubtotal(@PathVariable Integer userId) {
        return shoppingCartService.getCartSubtotal(userId);
    }
}