package com.example.SpringBootApps.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootApps.entity.AppUser;
import com.example.SpringBootApps.entity.CreditCard;
import com.example.SpringBootApps.repository.AppUserRepository;
import com.example.SpringBootApps.repository.CreditCardRepository;

@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public CreditCardService(CreditCardRepository creditCardRepository,
                             AppUserRepository appUserRepository) {
        this.creditCardRepository = creditCardRepository;
        this.appUserRepository = appUserRepository;
    }

    public void createCreditCardForUser(String username, Map<String, Object> body) {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String cardNumber = body.get("cardNumber") == null ? null : body.get("cardNumber").toString();
        String nameOnCard = body.get("nameOnCard") == null ? null : body.get("nameOnCard").toString();
        Integer expMonth = body.get("expMonth") == null ? null : Integer.valueOf(body.get("expMonth").toString());
        Integer expYear = body.get("expYear") == null ? null : Integer.valueOf(body.get("expYear").toString());

        if (cardNumber == null || cardNumber.isBlank() ||
            nameOnCard == null || nameOnCard.isBlank() ||
            expMonth == null || expYear == null) {
            throw new IllegalArgumentException("cardNumber, nameOnCard, expMonth, and expYear are required");
        }

        CreditCard card = new CreditCard();
        card.setUser(user);
        card.setCardNumber(cardNumber);
        card.setNameOnCard(nameOnCard);
        card.setExpMonth(expMonth);
        card.setExpYear(expYear);

        creditCardRepository.save(card);
    }
}