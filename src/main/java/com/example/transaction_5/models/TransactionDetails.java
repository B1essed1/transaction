package com.example.transaction_5.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDetails {

    private Long senderCardId;
    private Long receiverCardId;
    private Long amount;


}
