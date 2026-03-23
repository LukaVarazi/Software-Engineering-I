package com.example.SpringBootApps.service;
import com.example.SpringBootApps.entity.Book;
import com.example.SpringBootApps.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookBrowsingService {

    private final BookRepository bookRepository;

    @Autowired
    public BookBrowsingService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findBooksByGenre(String genre) {
        return bookRepository.findAllByGenre(genre);
    }

    public List<Book> findTop10Sellers(){
        return bookRepository.findAllTopSellers();
    }

    public List<Book> findGreaterThanEqualRating( int rating){
        return bookRepository.booksAboveRating(rating);
    }
}
