package com.example.mjuBank.repository;

import com.example.mjuBank.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // 특정 계좌의 거래내역 조회
    @Query("SELECT t FROM Transaction t WHERE t.account.accountId = :accountId ORDER BY t.transactionDate DESC, t.transactionNo DESC")
    List<Transaction> findHistoryByAccountId(@Param("accountId") Long accountId);

}