package com.example.mjuBank.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", length = 20)
    private Long cardId;

    @Column(name = "card_apply_date", nullable = false)
    private LocalDate cardApplyDate;

    @Column(name = "card_limit")
    private Long cardLimit;

    @Column(name = "card_payment_day", length = 10)
    private String cardPaymentDay;

    @Column(name = "card_type", length = 20)
    private String cardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false) //고객 필수
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false) //결제 계좌 필수
    private Account account;
}