package com.example.SpringBootApps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringBootApps.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // no extra methods needed
}