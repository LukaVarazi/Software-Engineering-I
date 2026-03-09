package com.example.SpringBootApps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringBootApps.entity.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}