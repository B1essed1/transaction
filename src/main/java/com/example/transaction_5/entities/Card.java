package com.example.transaction_5.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long balance;
    private String name;
    @Column(length = 16, unique = true)
    private String card_number;
    @Column(length = 10)
    private String type = "HUMO";
    @Column(length = 10)
    private String currency;

    @ManyToOne
    private Users users;

}
