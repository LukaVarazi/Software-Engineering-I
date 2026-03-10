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
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootApps.entity.Author;
import com.example.SpringBootApps.entity.Book;
import com.example.SpringBootApps.repository.BookRepository;
import com.example.SpringBootApps.service.BookService;

// controller class handles HTTP requests for REST API
@RestController
@RequestMapping("")
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    //===================================================================
    // BOOK DETAILS
    //===================================================================

    //create a new book.
    @PostMapping("/book")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        Book newBook = bookService.saveBook(book);
        return ResponseEntity.ok(newBook);
    }

    //get book by id
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.notFound().build());
    }

    //create a new Author.
    @PostMapping("/author")
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        System.out.println("Received author: " + author.getFirst_name() + " " + author.getLast_name());
        System.out.println("Biography: " + author.getBiography());  // Add this debug line
        System.out.println("Publisher: " + author.getPublisher());

        Author newAuthor = bookService.saveAuthor(author);
        return ResponseEntity.ok(newAuthor);
    }

    //get books by Author id
    @GetMapping("/authors/{id}")
    public List<Book> getBooksByAuthor(@PathVariable Long authorId){
        return bookRepository.findByAuthorId(authorId);
    }

    //===================================================================
    // REST
    //===================================================================

    //get all books
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    //update book by id.
    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    //delete book
    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }

    //===================================================================
}
