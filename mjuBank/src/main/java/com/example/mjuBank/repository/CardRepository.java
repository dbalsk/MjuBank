package com.example.mjuBank.repository;

import com.example.mjuBank.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    // 고객명으로 보유 카드 조회
    @Query("SELECT c FROM Card c JOIN FETCH c.customer cust JOIN FETCH c.account acc WHERE cust.customerName = :name")
    List<Card> findCardsByCustomerName(@Param("name") String name);
}