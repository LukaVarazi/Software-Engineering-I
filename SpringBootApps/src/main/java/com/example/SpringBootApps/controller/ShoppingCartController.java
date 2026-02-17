package com.example.SpringBootApps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootApps.entity.ShoppingCart;
import com.example.SpringBootApps.repository.ShoppingCartRepository;

// controller class handles HTTP requests for Shopping Cart REST API Only GET
@RestController
@RequestMapping("")
public class ShoppingCartController {

    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ShoppingCartController(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    // get all books in a user's shopping cart
    @GetMapping("/cart/{username}")
    public List<ShoppingCart> getUserCart(@PathVariable String username) {
        return shoppingCartRepository.findByUsername(username);
    }
}
