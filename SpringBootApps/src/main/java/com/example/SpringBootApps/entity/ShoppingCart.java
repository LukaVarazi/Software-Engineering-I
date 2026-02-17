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
@Table(name = "shopping_cart_table")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // user id (integer in database)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    // relationship to book entity
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    // getters
    public Long getId() { return id; }
    public Integer getUserId() { return userId; }
    public Book getBook() { return book; }
    public Integer getQuantity() { return quantity; }
    public Integer getTotalPrice() { return totalPrice; }

    // setters
    public void setId(Long id) { this.id = id; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public void setBook(Book book) { this.book = book; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setTotalPrice(Integer totalPrice) { this.totalPrice = totalPrice; }
}
