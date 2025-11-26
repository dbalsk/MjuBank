package com.example.mjuBank.controller;

import com.example.mjuBank.domain.Account;
import com.example.mjuBank.domain.Branch;
import com.example.mjuBank.domain.Customer;
import com.example.mjuBank.repository.AccountRepository;
import com.example.mjuBank.repository.BranchRepository;
import com.example.mjuBank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final BranchRepository branchRepository;

    // 계좌 목록 조회
    @GetMapping("/accounts")
    public String list(Model model) {
        List<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts", accounts);
        return "accounts/list";
    }

    // 계좌 개설 폼으로 이동
    @GetMapping("/accounts/new")
    public String createForm(Model model) {
        List<Customer> customers = customerRepository.findAll();
        List<Branch> branches = branchRepository.findAll();

        model.addAttribute("customers", customers);
        model.addAttribute("branches", branches);

        return "accounts/form";
    }

    // 계좌 개설 처리
    @PostMapping("/accounts/new")
    public String create(
            @RequestParam("customerId") Long customerId,
            @RequestParam("branchId") Long branchId,
            @RequestParam("accountType") String accountType,
            @RequestParam("cardApplied") String cardApplied
    ) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        Branch branch = branchRepository.findById(branchId).orElse(null);

        Account account = new Account();
        account.setCustomer(customer);
        account.setBranch(branch);
        account.setAccountType(accountType);
        account.setCardApplied(cardApplied);

        account.setAccountBalance(0L);
        account.setAccountOpenDate(LocalDate.now());

        account.setDepositorName(customer.getCustomerName());
        account.setDepositorPhone(customer.getCustomerPhone());
        account.setDepositorEmail(customer.getCustomerEmail());

        accountRepository.save(account);

        return "redirect:/accounts";
    }

    // 특정 고객의 계좌 조회 및 정렬
    @GetMapping("/accounts/search")
    public String search(@RequestParam(value = "customerId", required = false) Long customerId,
                         @RequestParam(value = "sort", required = false) String sort,
                         Model model) {

        // ID가 없으면 빈 화면 리턴
        if (customerId == null) {
            return "accounts/search";
        }

        List<Account> accounts;

        // 정렬 조건에 따라 다른 Repository 메서드 호출
        if ("date".equals(sort)) {
            accounts = accountRepository.findAccountsByCustomerIdOrderByDate(customerId);
        } else if ("balance".equals(sort)) {
            accounts = accountRepository.findAccountsByCustomerIdOrderByBalance(customerId);
        } else {
            accounts = accountRepository.findAccountsByCustomerId(customerId);
        }

        model.addAttribute("accounts", accounts);
        model.addAttribute("searchId", customerId);

        return "accounts/search";
    }
}
