package com.example.transaction_5.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
public class CardsDetails {
    private Long id;
    private Long balance;
    private String name;
    private String card_number;
    private String type = "HUMO";
    private String currency;
}
