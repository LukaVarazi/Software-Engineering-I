package com.example.SpringBootApps.repository;
import com.example.SpringBootApps.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// repository interface handles database operations for the BOOK entity.
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(
            value = "SELECT * FROM book_table WHERE genre LIKE CONCAT('%', :genre, '%')",
            nativeQuery = true
    )
    List<Book> findAllByGenre(@Param("genre") String genre);

    /*@Query(

    )
    List<Book> findTop10Sellers(@Param(""))

     */
}
