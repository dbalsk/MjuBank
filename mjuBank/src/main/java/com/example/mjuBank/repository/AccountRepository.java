package com.example.mjuBank.repository;

import com.example.mjuBank.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // 고객명으로 계좌 조회
    @Query("SELECT a FROM Account a JOIN FETCH a.customer c WHERE c.customerName = :name")
    List<Account> findAccountsByCustomerName(@Param("name") String name);

    // 고객명으로 계좌 조회 (개설일 순 정렬)
    @Query("SELECT a FROM Account a JOIN a.customer c WHERE c.customerName = :name ORDER BY a.accountOpenDate ASC")
    List<Account> findAccountsByCustomerNameOrderByDate(@Param("name") String name);

    // 고객명으로 계좌 조회 (잔액 많은 순 정렬)
    @Query("SELECT a FROM Account a JOIN a.customer c WHERE c.customerName = :name ORDER BY a.accountBalance DESC")
    List<Account> findAccountsByCustomerNameOrderByBalance(@Param("name") String name);

    // 입금 (잔액 증가)
    @Modifying
    @Query("UPDATE Account a SET a.accountBalance = a.accountBalance + :amount WHERE a.accountId = :accountId")
    int depositAmount(@Param("accountId") Long accountId, @Param("amount") Long amount);

    // 출금 (잔액 감소)
    @Modifying
    @Query("UPDATE Account a SET a.accountBalance = a.accountBalance - :amount WHERE a.accountId = :accountId")
    int withdrawAmount(@Param("accountId") Long accountId, @Param("amount") Long amount);

    // 직원 이름으로 담당 계좌 조회
    @Query("SELECT a FROM Account a JOIN a.employee e WHERE e.employeeName = :empName")
    List<Account> findAccountsByEmployeeName(@Param("empName") String empName);
}