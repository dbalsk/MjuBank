package com.example.mjuBank.repository;

import com.example.mjuBank.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // 특정 지점에 소속된 직원 목록 조회
    @Query("SELECT e FROM Employee e JOIN e.branch b WHERE b.branchName = :branchName")
    List<Employee> findEmployeesByBranchName(@Param("branchName") String branchName);

}