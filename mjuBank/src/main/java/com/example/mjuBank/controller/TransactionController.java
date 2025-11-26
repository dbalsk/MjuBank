package com.example.mjuBank.controller;

import com.example.mjuBank.domain.Account;
import com.example.mjuBank.repository.AccountRepository;
import com.example.mjuBank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TransactionController {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;

    // 입출금 페이지 이동
    @GetMapping("/transactions/new")
    public String form(Model model) {
        List<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts", accounts);
        return "transactions/form";
    }

    // 입출금 처리
    @PostMapping("/transactions/new")
    public String process(@RequestParam("accountId") Long accountId,
                          @RequestParam("type") String type,
                          @RequestParam("amount") Long amount,
                          @RequestParam("desc") String desc) {

        if ("DEPOSIT".equals(type)) {
            transactionService.deposit(accountId, amount, desc);
        } else if ("WITHDRAW".equals(type)) {
            try {
                transactionService.withdraw(accountId, amount, desc);
            } catch (IllegalStateException e) {
                // 잔액 부족 시 에러 페이지 대신 그냥 리스트로 보냄
                return "redirect:/accounts?error=balance";
            }
        }

        return "redirect:/accounts";
    }
}