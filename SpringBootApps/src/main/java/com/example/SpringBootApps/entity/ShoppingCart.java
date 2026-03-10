package com.example.SpringBootApps.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "shopping_cart_table")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    // Getters
    public Long getId() { return id; }
    public Integer getUserId() { return userId; }
    public Long getBookId() { return bookId; }
    public Integer getQuantity() { return quantity; }
    public Double getTotalPrice() { return totalPrice; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
}