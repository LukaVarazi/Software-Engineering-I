package com.example.SpringBootApps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringBootApps.entity.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    List<ShoppingCart> findByUserId(Integer userId);

    ShoppingCart findByUserIdAndBook_Id(Integer userId, Long bookId);

    void deleteByUserIdAndBook_Id(Integer userId, Long bookId);
}