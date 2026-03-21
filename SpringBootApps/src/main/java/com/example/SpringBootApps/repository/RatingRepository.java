package com.example.SpringBootApps.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.SpringBootApps.entity.Rating;


// repository interface handles database operations for the RATING entity.
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT AVG(r.rateValue) FROM Rating r WHERE r.bookId = :bookId")
    Double getAverageRating(@Param("bookId") Long bookId);
}
