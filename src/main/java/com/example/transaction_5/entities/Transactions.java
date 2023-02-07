package com.example.transaction_5.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "transaction")
public class Transactions {
    @Id
    private String id = UUID.randomUUID().toString();
    private Long senderCardId;
    private Long receiverCardId;
    private Long senderAmount;
    private Long receiverAmount;
    @Column(length = 10)
    private String status;
    private Long time;


}
