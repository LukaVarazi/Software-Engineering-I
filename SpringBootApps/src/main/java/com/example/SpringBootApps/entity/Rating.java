package com.example.SpringBootApps.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//entity classes (for rating entities/rating objects)
//includes getters and setters for rating attributes.

@Entity
@Table(name = "rate_table")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false)
    private Long rateValue;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    //getters and setters

    // Getters
    public Long getId() {
        return id;}
    
    public Long getUserId() {
        return userId;}

    public Long getbookId() {
        return bookId;}

    public Long getRating() {
        return rateValue;}

    public LocalDateTime getDate() {
        return createdAt;}


    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setISBN(Long bookId) {
        this.bookId = bookId;
    }

    public void setRating(Long rateValue) {
        this.rateValue = rateValue;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}