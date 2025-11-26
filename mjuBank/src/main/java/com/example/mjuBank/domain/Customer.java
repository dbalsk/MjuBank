package com.example.mjuBank.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", length = 14)
    private Long customerId;

    @Column(name = "customer_name", length = 50, nullable = false)
    private String customerName;

    @Column(name = "customer_address", length = 100)
    private String customerAddress;

    @Column(name = "customer_birth")
    private LocalDate customerBirth;

    @Column(name = "customer_email", length = 100)
    private String customerEmail;

    @Column(name = "customer_phone", length = 20)
    private String customerPhone;

    @Column(name = "customer_job", length = 50)
    private String customerJob;
}