package com.sgedts.wallet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sgedts.wallet.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @Column(name = "username")
    private String username;
    @Column(name = "amount")
    private Long amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "balance_before")
    private Long balanceBefore;
    @Column(name = "balance_after")
    private Long balanceAfter;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;
}
