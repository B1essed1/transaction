package com.example.transaction_5.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CardInfo {
    public CardInfo(String phone, String cardNumber, String type, Long id, String userId) {
        this.phone = phone;
        this.cardNumber = cardNumber;
        this.type = type;
        this.id = id;
        this.userId = userId;
    }

    private String phone;
    private String cardNumber;
    private String type;
    private Long id;
    private String userId;
}
