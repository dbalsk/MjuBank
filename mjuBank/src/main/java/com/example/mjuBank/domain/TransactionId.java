package com.example.mjuBank.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
class TransactionId implements Serializable {
    //PK 3개이기에 복합키 클래스로 분리
    private Long accountId;
    private LocalDateTime transactionDate;
    private Integer transactionNo;
}