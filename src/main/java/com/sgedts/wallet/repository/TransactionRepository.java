package com.sgedts.wallet.repository;

import com.sgedts.wallet.model.Transaction;
import com.sgedts.wallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    User findByUsername(String username);
}
