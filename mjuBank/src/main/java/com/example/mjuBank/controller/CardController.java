package com.example.mjuBank.controller;

import com.example.mjuBank.domain.Account;
import com.example.mjuBank.domain.Card;
import com.example.mjuBank.domain.Customer;
import com.example.mjuBank.repository.AccountRepository;
import com.example.mjuBank.repository.CardRepository;
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
public class CardController {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    // 카드 목록 조회
    @GetMapping("/cards")
    public String list(@RequestParam(value = "customerId", required = false) Long customerId, Model model) {
        List<Card> cards;

        if (customerId != null) {
            // 특정 고객의 카드만 조회
            cards = cardRepository.findCardsByCustomerId(customerId);
        } else {
            // 전체 카드 조회
            cards = cardRepository.findAll();
        }

        model.addAttribute("cards", cards);
        model.addAttribute("searchId", customerId);
        return "cards/list";
    }

    // 카드 발급 폼 이동
    @GetMapping("/cards/new")
    public String createForm(Model model) {
        // 카드를 연결할 계좌 목록을 가져옵니다.
        List<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts", accounts);
        return "cards/form";
    }

    // 카드 발급 처리
    @PostMapping("/cards/new")
    public String create(@RequestParam("accountId") Long accountId,
                         @RequestParam("cardType") String cardType,
                         @RequestParam("cardLimit") Long cardLimit,
                         @RequestParam("cardPaymentDay") String cardPaymentDay) {

        // 연결할 계좌 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Account ID"));

        // 계좌의 주인 정보 가져오기
        Customer customer = account.getCustomer();

        // 카드 생성
        Card card = new Card();
        card.setAccount(account);
        card.setCustomer(customer);
        card.setCardType(cardType);
        card.setCardLimit(cardLimit);
        card.setCardPaymentDay(cardPaymentDay);
        card.setCardApplyDate(LocalDate.now());

        cardRepository.save(card);

        return "redirect:/cards";
    }
}