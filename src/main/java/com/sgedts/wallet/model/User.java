package com.sgedts.wallet.model;

import com.sgedts.wallet.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
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
    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;
}
