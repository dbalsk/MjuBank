package com.example.mjuBank.controller;

import com.example.mjuBank.domain.Branch;
import com.example.mjuBank.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BranchController {

    private final BranchRepository branchRepository;

    // 지점 검색 및 상세 조회 페이지
    @GetMapping("/branches/search")
    public String search(@RequestParam(value = "branchName", required = false) String branchName, Model model) {

        // 전체 지점 목록
        List<Branch> allBranches = branchRepository.findAll();
        model.addAttribute("allBranches", allBranches);

        // 검색어가 있으면 상세 정보 조회
        if (branchName != null && !branchName.isBlank()) {
            Branch branch = branchRepository.findBranchByName(branchName);
            model.addAttribute("branch", branch);
            model.addAttribute("searchName", branchName);
        }

        return "branches/search";
    }
}