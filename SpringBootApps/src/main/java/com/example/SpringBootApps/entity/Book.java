package com.example.SpringBootApps.entity;
import jakarta.persistence.*;

//entity classes (for book entities/book objects)
//includes getters and setters for book attributes.

@Entity
@Table(name = "book_table")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String ISBN;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long author_id;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int copies_sold;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private double discount_percent;

    @Column(nullable = false)
    private String book_description;

    @Column(nullable = false)
    private String year_published;


    //getters and setters

    // Getters
    public Long getId() {
        return id;}

    public String getISBN() {
        return ISBN;}

    public String getTitle() {
        return title;}

    public Long getAuthor() {
        return author_id;}

    public String getGenre() {
        return genre;}

    public String getPublisher() {
        return publisher;}

    public double getPrice() {
        return price;}

    public int getCopies_sold() {
        return copies_sold;}

    public int getRating() {
        return rating;}

    public double getDiscount_percent() {
        return discount_percent;}

    public String getBook_description() {
        return book_description;}

    public String getYear_published() {
        return year_published;}

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAuthor(Long author_id) {
        this.author_id = author_id;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCopies_sold(int copies_sold) {
        this.copies_sold = copies_sold;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public void setDiscount_percent(double discount_percent) {
        this.discount_percent = discount_percent;
    }
    public void setBook_description(String book_description) {
        this.book_description = book_description;
    }
    public void setYear_published(String year_published) {
        this.year_published = year_published;
    }

}
