package com.example.SpringBootApps.controller;
import com.example.SpringBootApps.entity.Book;
import com.example.SpringBootApps.service.BookBrowsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// controller class handles HTTP requests for REST API
@RestController
@RequestMapping("")
public class BookBrowsing_Controller {
    private final BookBrowsingService bookService;
    private final BookBrowsingService bookBrowsingService;


    @Autowired
    public BookBrowsing_Controller(BookBrowsingService bookService, BookBrowsingService bookBrowsingService) {
        this.bookService = bookService;
        this.bookBrowsingService = bookBrowsingService;
    }

    //GET list of books by genre
    @GetMapping("/books/genre/{genre}")
    public List<Book> findBooksByGenre(@PathVariable String genre) {
        return bookBrowsingService.findBooksByGenre(genre);
    }

    //GET list of top 10 sellers

}