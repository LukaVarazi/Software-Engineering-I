package com.example.SpringBootApps.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootApps.entity.Book;
import com.example.SpringBootApps.entity.Wishlist;
import com.example.SpringBootApps.service.WishlistService;

@RestController
@RequestMapping("")
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // Create a wishlist for a specific user
    @PostMapping("/users/{userId}/wishlists")
    public ResponseEntity<Wishlist> createWishlist(
            @PathVariable Long userId,
            @RequestBody Wishlist wishlist) {

        Wishlist newWishlist = wishlistService.createWishlist(userId, wishlist);
        return ResponseEntity.ok(newWishlist);
    }

    // Add a book
    @PostMapping("/wishlists/{wishlistId}/books/{bookId}")
    public ResponseEntity<Void> addBookToWishlist(
        @PathVariable Long wishlistId,
        @PathVariable Long bookId) {

        wishlistService.addBookToWishlist(wishlistId, bookId);
        return ResponseEntity.ok().build();
    }

    // Get wishlist by ID
    @GetMapping("/wishlists/{id}")
    public ResponseEntity<Wishlist> getWishlistById(@PathVariable Long id) {
        Optional<Wishlist> wishlist = wishlistService.getWishlistById(id);
        return wishlist.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Remove a book from a wishlist and move it to shopping cart
    @DeleteMapping("/wishlists/{wishlistId}/books/{bookId}")
    public ResponseEntity<Void> moveBookToCart(
        @PathVariable Long wishlistId,
        @PathVariable Long bookId) {

        wishlistService.moveBookToCart(wishlistId, bookId);
        return ResponseEntity.noContent().build();
    }

    // Get all books in a wishlist
    @GetMapping("/wishlists/{wishlistId}/books")
    public ResponseEntity<List<Book>> getBooksInWishlist(@PathVariable Long wishlistId) {
        List<Book> books = wishlistService.getBooksInWishlist(wishlistId);
        return ResponseEntity.ok(books);
    }
}
