package com.sgedts.wallet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "ktp")
    private String ktp;
    @Column(name = "status")
    private Boolean isBan;

    @Column(name = "balance")
    private Long balance;
    @Column(name = "transaction_limit")
    private Long transactionLimit;
}