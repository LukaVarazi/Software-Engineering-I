package com.example.SpringBootApps.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @PostMapping("/add-book")
    public ResponseEntity<Void> addBookToWishlist(
        @RequestParam Long wishlistId,
        @RequestParam Long bookId) {

        wishlistService.addBookToWishlist(wishlistId, bookId);
        return ResponseEntity.ok().build();
    }

    // Get all wishlists
    @GetMapping("/wishlists")
    public List<Wishlist> getAllWishlists() {
        return wishlistService.getAllWishlists();
    }

    // Get wishlist by ID
    @GetMapping("/wishlists/{id}")
    public ResponseEntity<Wishlist> getWishlistById(@PathVariable Long id) {
        Optional<Wishlist> wishlist = wishlistService.getWishlistById(id);
        return wishlist.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a wishlist
    @PutMapping("/wishlists/{id}")
    public ResponseEntity<Wishlist> updateWishlist(@PathVariable Long id, @RequestBody Wishlist wishlist) {
        Wishlist updatedWishlist = wishlistService.updateWishlist(id, wishlist);
        return ResponseEntity.ok(updatedWishlist);
    }

    // Delete a wishlist
    @DeleteMapping("/wishlists/{id}")
    public ResponseEntity<String> deleteWishlist(@PathVariable Long id) {
        wishlistService.deleteWishlist(id);
        return ResponseEntity.ok("Wishlist deleted successfully");
    }

    // Get all books in a wishlist
    @GetMapping("/wishlists/{id}/books")
    public ResponseEntity<List<Book>> getBooksInWishlist(@PathVariable Long id) {
        Optional<Wishlist> wishlist = wishlistService.getWishlistById(id);
        if (wishlist.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(wishlist.get().getBooks());
    }
}
