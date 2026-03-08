package com.example.SpringBootApps.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// entity class (for shopping cart items)
// represents books added to a user's shopping cart.

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // username of the cart owner
    @Column(nullable = false)
    private String username;

    // relationship to book entity
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private int quantity;

    // getters

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }

    // setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
