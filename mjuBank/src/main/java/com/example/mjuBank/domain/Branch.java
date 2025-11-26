package com.example.mjuBank.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "branch")
public class Branch {

    @Id
    @Column(name = "branch_id", length = 10)
    private String branchId;

    @Column(name = "branch_name", length = 50, nullable = false)
    private String branchName;

    @Column(name = "branch_address", length = 100)
    private String branchAddress;

    @Column(name = "branch_phone", length = 20)
    private String branchPhone;

    @Column(name = "branch_manager", length = 50)
    private String branchManager;
}