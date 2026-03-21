package com.example.SpringBootApps.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootApps.entity.Comment;
import com.example.SpringBootApps.entity.Rating;
import com.example.SpringBootApps.service.RateAndCommentService;

// controller class handles HTTP requests for REST API
@RestController
@RequestMapping("")
public class RateAndCommentController {

    private final RateAndCommentService rateAndCommentService;

    @Autowired
    public RateAndCommentController(RateAndCommentService rateAndCommentService) {
        this.rateAndCommentService = rateAndCommentService;
    }

    //===================================================================
    // RATING
    //===================================================================

    //create a new book.
    @PostMapping("/rating")
    public ResponseEntity<?> saveRating(@RequestBody Rating rating) {
        try {
            Rating savedRating = rateAndCommentService.saveRating(rating);
            return ResponseEntity.ok(savedRating);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //get average rating by bookId
    @GetMapping("/ratings/{bookId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long bookId) {

        List<Long> ratings = rateAndCommentService.findAllRatings(bookId);

        if (ratings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        double avg = ratings.stream().mapToLong(Long::longValue).average().orElse(0);

        return ResponseEntity.ok(avg);
    }

    //===================================================================
    // COMMENTING
    //===================================================================

    // Create a comment
    @PostMapping("/comment")
    public ResponseEntity<?> saveComment(@RequestBody Comment comment) {
        try {
            Comment savedComment = rateAndCommentService.saveComment(comment);
            return ResponseEntity.ok(savedComment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get Comments
    @GetMapping("/comments/{bookId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long bookId) {
        List<Comment> comments = rateAndCommentService.getCommentsByBook(bookId);

        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(comments);
    }
    //===================================================================
}
