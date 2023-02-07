package com.example.transaction_5.repositories;

import com.example.transaction_5.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, String> {

    Optional<Transactions> findTransactionsByStatusAndSenderCardId(String status, Long cardId);
}
