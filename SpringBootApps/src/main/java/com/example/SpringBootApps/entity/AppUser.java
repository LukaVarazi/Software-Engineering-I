// =========================== entity/AppUser.java =============================
package com.example.SpringBootApps.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
// IMPORTANT: match this EXACTLY to your DB table name.
// Based on your successful POST, your table name appears to be "appuser_table".
@Table(name = "appuser_table")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // IMPORTANT: match this EXACTLY to your DB primary key column name.
    // Since your POST response returned userId=1, this should map to the real PK column.
    // If your DB PK column is NOT user_id, adjust the name below.
    @Column(name = "id")
    private Long userId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    // Stored in DB column password_hash (even if plaintext for now)
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(length = 100)
    private String name;

    @Column(length = 120)
    private String email;

    @Column(name = "home_address", length = 255)
    private String homeAddress;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getHomeAddress() { return homeAddress; }
    public void setHomeAddress(String homeAddress) { this.homeAddress = homeAddress; }
}
