package com.example.mjuBank.controller;

import com.example.mjuBank.domain.Account;
import com.example.mjuBank.domain.Employee;
import com.example.mjuBank.repository.AccountRepository;
import com.example.mjuBank.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final AccountRepository accountRepository;

    // 직원별 담당 계좌 조회 페이지
    @GetMapping("/employees/accounts")
    public String searchAccounts(@RequestParam(value = "empName", required = false) String empName, Model model) {

        // 직원 목록
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);

        // 검색어가 있으면 담당 계좌 조회
        if (empName != null && !empName.isBlank()) {
            List<Account> accounts = accountRepository.findAccountsByEmployeeName(empName);
            model.addAttribute("accounts", accounts);
            model.addAttribute("searchName", empName);
        }

        return "employees/accounts";
    }
}