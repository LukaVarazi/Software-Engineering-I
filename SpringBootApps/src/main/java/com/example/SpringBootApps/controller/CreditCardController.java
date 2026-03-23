package com.example.SpringBootApps.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootApps.service.CreditCardService;

@RestController
@RequestMapping("")
public class CreditCardController {

    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping("/users/{username}/credit-cards")
    public ResponseEntity<?> createCreditCard(@PathVariable String username,
                                              @RequestBody Map<String, Object> body) {
        creditCardService.createCreditCardForUser(username, body);
        return ResponseEntity.ok(Map.of("message", "Credit card added successfully"));
    }
}