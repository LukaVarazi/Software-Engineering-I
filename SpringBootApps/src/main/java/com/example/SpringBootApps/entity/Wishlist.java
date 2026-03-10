package com.example.SpringBootApps.entity;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

// Entity for wishlist/wishlist table
@Entity
@Table(
    name = "wishlists_table",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "user_id"})
})
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @ManyToMany
    @JoinTable(
        name = "wishlist_books",
        joinColumns = @JoinColumn(name = "wishlist_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )

    private List<Book> books;

    //getters and setters

    //getters
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public AppUser getUser() {
        return user;
    }

    public List<Book> getBooks() {
        return books;
    }

    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
