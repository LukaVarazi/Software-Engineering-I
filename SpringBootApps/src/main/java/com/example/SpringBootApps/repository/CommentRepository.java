package com.example.SpringBootApps.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.SpringBootApps.entity.Comment;


// repository interface handles database operations for the COMMENT entity.
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.bookId = :bookId")
    List<Comment> findByBookId(@Param("bookId") Long bookId);
}
