package com.example.mjuBank.repository;

import com.example.mjuBank.domain.Branch;
import com.example.mjuBank.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    // 지점명으로 상세 정보 조회
    @Query("SELECT b FROM Branch b WHERE b.branchName = :branchName")
    Branch findBranchByName(@Param("branchName") String branchName);

}
