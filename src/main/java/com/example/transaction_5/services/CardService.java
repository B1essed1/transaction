package com.example.transaction_5.services;

import com.example.transaction_5.models.CardDto;
import com.example.transaction_5.models.ResponseDto;

public interface CardService {
    ResponseDto<?> addCard(CardDto dto);

    ResponseDto <?> getMyCards(String id);

    ResponseDto <?> getCardInfo(String cardNumber);
}
