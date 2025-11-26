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
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "employee_id", length = 10)
    private String employeeId;

    @Column(name = "employee_name", length = 50, nullable = false)
    private String employeeName;

    @Column(name = "employee_phone", length = 20)
    private String employeePhone;

    @Column(name = "employee_hire_date", nullable = false)
    private LocalDate employeeHireDate;

    @Column(name = "employee_salary")
    private Long employeeSalary;

    // 지점은 NULL 허용 (발령 대기 상태 등 고려)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;
}