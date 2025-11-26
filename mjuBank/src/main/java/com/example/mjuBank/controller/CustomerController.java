package com.example.mjuBank.controller;

import com.example.mjuBank.domain.Customer;
import com.example.mjuBank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    // 고객 목록 조회
    @GetMapping("/customers")
    public String list(Model model) {
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        return "customers/list";
    }

    // 신규 고객 등록 페이지 이동
    @GetMapping("/customers/new")
    public String createForm() {
        return "customers/form";
    }

    // 신규 고객 등록 처리
    @PostMapping("/customers/new")
    public String create(Customer customer) {
        customerRepository.save(customer);
        return "redirect:/customers";
    }
}