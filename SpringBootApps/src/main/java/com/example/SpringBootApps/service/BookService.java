package com.example.SpringBootApps.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootApps.entity.Author;
import com.example.SpringBootApps.entity.Book;
import com.example.SpringBootApps.repository.AuthorRepository;
import com.example.SpringBootApps.repository.BookRepository;


/*
Service class managing Book entities.
This class lists all the services/functions that the program perform on Book entities
ex. delete book, save new book, update any attribute of a book, etc...
 */
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    //===================================================================
    // BOOK DETAILS
    //===================================================================

    //saving a book
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    //get book by id
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    //Save Author
    public Author saveAuthor(Author author) {
    return authorRepository.save(author);
    }

    public List<Book> findBooksByAuthorId(Long author_id) {
    return bookRepository.findByAuthorId(author_id);
    }

    //===================================================================
    // REST
    //===================================================================

    //get list of all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book updateBook(Long id, Book book) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Book updatedBook = existingBook.get();
            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setISBN(book.getISBN());
            updatedBook.setGenre(book.getGenre());
            updatedBook.setPublisher(book.getPublisher());
            updatedBook.setPrice(book.getPrice());
            updatedBook.setDiscount_percent(book.getDiscount_percent());
            updatedBook.setRating(book.getRating());
            updatedBook.setBook_description(book.getBook_description());
            updatedBook.setCopies_sold(book.getCopies_sold());
            updatedBook.setYear_published(book.getYear_published());
            return bookRepository.save(updatedBook);
        }else {
            throw new RuntimeException("Book not found");
        }
    }

    // delete a book by ID.
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    //===================================================================
}
