package com.example.mjuBank.service;

import com.example.mjuBank.domain.Account;
import com.example.mjuBank.domain.Transaction;
import com.example.mjuBank.repository.AccountRepository;
import com.example.mjuBank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    // 입금 처리
    public void deposit(Long accountId, Long amount, String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계좌입니다."));

        // 잔액 증가 (더티 체킹으로 자동 update)
        account.setAccountBalance(account.getAccountBalance() + amount);

        // 거래내역 기록
        saveTransaction(account, "입금", amount, description);
    }

    // 출금 처리
    public void withdraw(Long accountId, Long amount, String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계좌입니다."));

        // 잔액 체크
        if (account.getAccountBalance() < amount) {
            throw new IllegalStateException("잔액이 부족합니다.");
        }

        // 잔액 감소
        account.setAccountBalance(account.getAccountBalance() - amount);

        // 거래내역 기록
        saveTransaction(account, "출금", amount, description);
    }

    // 거래내역 생성 및 저장
    private void saveTransaction(Account account, String type, Long amount, String description) {
        LocalDateTime now = LocalDateTime.now();

        // 오늘 날짜의 거래 순번 계산
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = now.toLocalDate().atTime(23, 59, 59);
        Integer maxNo = transactionRepository.findMaxTransactionNo(account.getAccountId(), startOfDay, endOfDay);

        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getAccountId());
        transaction.setTransactionDate(now);
        transaction.setTransactionNo(maxNo + 1);
        transaction.setTransactionType(type);
        transaction.setTransactionAmount(amount);
        transaction.setBalanceAfter(account.getAccountBalance());
        transaction.setTransactionDesc(description);

        transactionRepository.save(transaction);
    }
}
