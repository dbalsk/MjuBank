package com.example.mjuBank.repository;

import com.example.mjuBank.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // 특정 계좌의 거래내역 조회
    @Query("SELECT t FROM Transaction t WHERE t.account.accountId = :accountId ORDER BY t.transactionDate DESC, t.transactionNo DESC")
    List<Transaction> findHistoryByAccountId(@Param("accountId") Long accountId);

    // 오늘 날짜의 해당 계좌 거래 중 가장 큰 번호 조회 -> transaction_no 계산용
    @Query("SELECT COALESCE(MAX(t.transactionNo), 0) FROM Transaction t " +
            "WHERE t.accountId = :accountId AND t.transactionDate BETWEEN :start AND :end")
    Integer findMaxTransactionNo(@Param("accountId") Long accountId,
                                 @Param("start") LocalDateTime start,
                                 @Param("end") LocalDateTime end);
}