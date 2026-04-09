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

    @Autowired
    private ShoppingCartService shoppingCartService;


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

    // Get wishlist by ID
    public Optional<Wishlist> getWishlistById(Long id) {
        return wishlistRepository.findById(id);
    }

    //Remove book from wishlist and move it to shopping cart
    public void moveBookToCart(Long wishlistId, Long bookId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
            .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));

        //Add to cart
        shoppingCartService.addBookToCart(wishlist.getUser().getUserId().intValue(), bookId);

        //Remove book from wishlist
        wishlist.getBooks().remove(book);
        wishlistRepository.save(wishlist);
    }

    //Get all books in a wishlist
    public List<Book> getBooksInWishlist(Long wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
            .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        return wishlist.getBooks();
    }
}