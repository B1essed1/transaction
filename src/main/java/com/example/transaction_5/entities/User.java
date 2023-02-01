package com.example.transaction_5.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String phone;
    private String password;

    @Column(length = 10)
    private String status= "ACTIVE";


}
