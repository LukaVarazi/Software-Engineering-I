package com.example.SpringBootApps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringBootApps.entity.ShoppingCart;

// repository interface handles database operations for the SHOPPING CART entity.
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    // find all cart items by username
    List<ShoppingCart> findByUsername(String username);
}
