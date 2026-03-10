package com.example.SpringBootApps.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringBootApps.entity.Author;

// repository interface handles database operations for the AUTHOR entity.
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    
}
