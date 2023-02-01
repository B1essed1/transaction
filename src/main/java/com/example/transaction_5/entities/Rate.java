package com.example.transaction_5.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Rate {
    @Id
    private Long id;
    @Column(length =10)
    private String from_currency;

    @Column(length =10)
    private String to_currency;
    private Long rate;

}
