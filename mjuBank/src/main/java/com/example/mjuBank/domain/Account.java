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
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "account_id", length = 20)
    private String accountId;

    @Column(name = "account_type", length = 20, nullable = false)
    private String accountType;

    @Column(name = "account_balance", nullable = false)
    private Long accountBalance = 0L;

    @Column(name = "card_applied", length = 1, nullable = false)
    private String cardApplied = "N";

    @Column(name = "account_open_date", nullable = false)
    private LocalDate accountOpenDate;

    @Column(name = "depositor_name", length = 50)
    private String depositorName;

    @Column(name = "depositor_phone", length = 20)
    private String depositorPhone;

    @Column(name = "depositor_email", length = 100)
    private String depositorEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_ssn", nullable = false) //고객은 필수
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}