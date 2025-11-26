package com.example.mjuBank.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TransactionId.class) //복합키 클래스 연결
@Table(name = "`transaction`")
public class Transaction {

    @Id
    @Column(name = "account_id", length = 20)
    private String accountId;

    @Id
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Id
    @Column(name = "transaction_no")
    private Integer transactionNo;

    @Column(name = "transaction_type", length = 10, nullable = false)
    private String transactionType;

    @Column(name = "transaction_desc", length = 100)
    private String transactionDesc;

    @Column(name = "transaction_amount", nullable = false)
    private Long transactionAmount = 0L;

    @Column(name = "balance_after", nullable = false)
    private Long balanceAfter = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    private Account account;
}