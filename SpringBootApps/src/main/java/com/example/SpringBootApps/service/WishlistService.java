package com.example.SpringBootApps.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootApps.entity.AppUser;
import com.example.SpringBootApps.entity.Book;
import com.example.SpringBootApps.entity.Wishlist;
import com.example.SpringBootApps.repository.AppUserRepository;
import com.example.SpringBootApps.repository.BookRepository;
import com.example.SpringBootApps.repository.WishlistRepository;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private BookRepository bookRepository;


    // Create a wishlist for a user
    public Wishlist createWishlist(Long userId, Wishlist wishlist) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (wishlistRepository.existsByUser_UserIdAndName(userId, wishlist.getName())) {
            throw new RuntimeException("Wishlist name already exists for this user.");
        }

        if (wishlistRepository.countByUser_UserId(userId) >= 4) {
            throw new RuntimeException("Maximum wishlist limit reached.");
        }

        wishlist.setUser(user);
        return wishlistRepository.save(wishlist);
    }

    // Add book to wishlist
    public void addBookToWishlist(Long wishlistId, Long bookId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
            .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));

        wishlist.getBooks().add(book);
        wishlistRepository.save(wishlist);
    }

    // Get all wishlists
    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.findAll();
    }

    // Get wishlist by ID
    public Optional<Wishlist> getWishlistById(Long id) {
        return wishlistRepository.findById(id);
    }

    // Update wishlist
    public Wishlist updateWishlist(Long id, Wishlist updatedWishlist) {
        Wishlist wishlist = wishlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wishlist not found."));

        wishlist.setName(updatedWishlist.getName());
        // Update other fields if needed
        return wishlistRepository.save(wishlist);
    }

    // Delete wishlist
    public void deleteWishlist(Long id) {
        wishlistRepository.deleteById(id);
    }
}