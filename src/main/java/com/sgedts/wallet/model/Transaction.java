package com.sgedts.wallet.model;

import com.sgedts.wallet.audit.Auditable;
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
@Table(name = "transaction")
public class Transaction extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @Column(name = "origin_username")
    private String originUsername;
    @Column(name = "destination_username")
    private String destinationUsername;
    @Column(name = "amount")
    private Long amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
//    @Column(name = "change_in_percentage")
//    private Double changeInPercentage;
//    @Column(name = "balance_change_date")
//    private Double balanceChangeDate;
}
