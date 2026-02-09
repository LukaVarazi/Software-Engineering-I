package com.example.SpringBootApps.repository;
import com.example.SpringBootApps.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// repository interface handles database operations for the BOOK entity.
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
