package com.example.transaction_5.services;

import com.example.transaction_5.models.ResponseDto;
import com.example.transaction_5.models.TransactionDetails;

public interface TransactionService {
    ResponseDto<?> requestTransaction(TransactionDetails transactionDetails);
    ResponseDto<?> confirm(String id);
}
