package com.example.transaction_5.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    private String id = UUID.randomUUID().toString();
    private Long sender_card_id;
    private Long receiver_card_id;
    private Long sender_amount;
    private Long receiver_amount;
    @Column(length = 10)
    private String status;
    private Date time;


}
