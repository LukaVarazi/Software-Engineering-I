package com.example.SpringBootApps.service;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootApps.entity.Comment;
import com.example.SpringBootApps.entity.Rating;
import com.example.SpringBootApps.repository.BookRepository;
import com.example.SpringBootApps.repository.CommentRepository;
import com.example.SpringBootApps.repository.RatingRepository;


/*
Service class managing Book entities.
This class lists all the services/functions that the program perform on Book entities
ex. delete book, save new book, update any attribute of a book, etc...
 */
@Service
public class RateAndCommentService {
    private final RatingRepository rateRepository;
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Autowired
    public RateAndCommentService(RatingRepository rateRepository,
                       CommentRepository commentRepository, BookRepository bookRepository) {
        this.rateRepository = rateRepository;
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    //===================================================================
    // RATING
    //===================================================================

    //Create a rating
    public Rating saveRating(Rating rating) {
        if (!bookRepository.existsById(rating.getBookId())) {
            throw new RuntimeException("Book does not exist");
        }
        
        Long rateValue = rating.getRateValue();
        if (rateValue == null || rateValue < 1 || rateValue > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }
        
        rating.setCreatedAt(LocalDateTime.now());
        return rateRepository.save(rating);
    }

    // Retreive Average Ratings
    public Double findAverageRating(Long bookId) {
        return rateRepository.getAverageRating(bookId);
    }

    //===================================================================
    // COMMENTING
    //===================================================================

    // Create a comment
    public Comment saveComment(Comment comment) {
        if (!bookRepository.existsById(comment.getBookId())) {
            throw new RuntimeException("Book does not exist");
        }
        
        // Add comment validation
        if (comment.getComment() == null || comment.getComment().trim().isEmpty()) {
            throw new RuntimeException("Comment cannot be empty");
        }
        
        if (comment.getComment().length() > 1000) {
            throw new RuntimeException("Comment exceeds maximum length of 1000 characters");
        }
        
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    // Retrieve Comments
    public List<Comment> getCommentsByBook(Long bookId) {
        return commentRepository.findByBookId(bookId);
    }
}