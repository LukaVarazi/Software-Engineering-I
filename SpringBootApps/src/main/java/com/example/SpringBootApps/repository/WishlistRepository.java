package com.example.SpringBootApps.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringBootApps.entity.Wishlist;

//repository for wishlist entity
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long>{
    boolean existsByUser_UserIdAndName(Long userId, String name);
    long countByUser_UserId(Long userId);

    List<Wishlist> findByUser_UserId(Long userId);

}