package com.example.transaction_5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Long sender_card_id;
    private Long receiver_card_id;
    private Long sender_amount;
    private Long receiver_amount;
    @Column(length = 10)
    private String status;
    private Date time;


}
