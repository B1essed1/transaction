package com.example.transaction_5.controller;

import com.example.transaction_5.models.ResponseDto;
import com.example.transaction_5.models.TransactionDetails;
import com.example.transaction_5.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/transaction/")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("hold")
    ResponseEntity<?> requestTransaction(@RequestBody TransactionDetails details){

        ResponseDto response = transactionService.requestTransaction(details);
        if (response.getIsError()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }

        return ResponseEntity.status(201).body(response.getData());
    }

    @PostMapping("confirm")
    ResponseEntity<?> confirm(@RequestParam String id){
        ResponseDto response = transactionService.confirm(id);
        if(response.getIsError()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response.getMessage());
        }
        return ResponseEntity.status(200).body(response.getMessage());
    }

}
