package com.example.SpringBootApps.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.SpringBootApps.entity.Book;

// repository interface handles database operations for the BOOK entity.
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(
        value = "SELECT * FROM book_table WHERE authorId LIKE CONCAT('%', :authorId, '%')",
        nativeQuery=true
    )
    List<Book> findByAuthorId(@Param("authorId") Long authorId);
}
