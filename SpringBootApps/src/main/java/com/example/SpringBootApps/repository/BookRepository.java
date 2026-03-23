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
            value = "SELECT * FROM book_table WHERE genre LIKE CONCAT('%', :genre, '%')",
            nativeQuery = true
    )
    List<Book> findAllByGenre(@Param("genre") String genre);

    @Query(
            value = "SELECT * FROM book_table WHERE author_id = :author_id",
            nativeQuery=true
    )
    List<Book> findByAuthorId(@Param("author_id") Long authorId);

    //retrieves live data records of top 10 most sold books in descending order of sales.
    @Query(
            value = "SELECT * FROM book_table ORDER BY copies_sold DESC LIMIT 10",
            nativeQuery = true
    )
    List<Book> findAllTopSellers();

    //retrieves list of books equal to or above specified rating
    @Query(
            value = "SELECT * FROM book_table WHERE book_table.rating >= :rating",
            nativeQuery = true
    )
    List<Book> booksAboveRating(@Param("rating") int rating);


}
