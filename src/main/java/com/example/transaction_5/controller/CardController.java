package com.example.transaction_5.controller;

import com.example.transaction_5.models.CardDto;
import com.example.transaction_5.models.ResponseDto;
import com.example.transaction_5.services.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card/")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("add")
    ResponseEntity<?> addCard(@RequestBody CardDto cardDto) {
        ResponseDto response = cardService.addCard(cardDto);
        if (response.getIsError()) {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response.getMessage());
    }

    @GetMapping("mine")
    ResponseEntity<?> myCards(@RequestParam("id") String id) {
        ResponseDto response = cardService.getMyCards(id);
        if (response.getIsError()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }

        return ResponseEntity.ok(response.getData());
    }


    /*@GetMapping("info")
    ResponseEntity<?> cardInfo(@RequestParam("card") String cardNumber){
        ResponseDto response = cardService.getCardInfo(cardNumber);

    }*/



}
