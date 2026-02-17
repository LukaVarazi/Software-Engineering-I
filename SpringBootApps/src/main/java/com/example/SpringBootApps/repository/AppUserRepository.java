
package com.example.SpringBootApps.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringBootApps.entity.AppUser;

/**
 * JPA repository for AppUser.
 * Spring generates SQL for these methods automatically.
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByUsername(String username);
    Optional<AppUser> findByUsername(String username);
}
