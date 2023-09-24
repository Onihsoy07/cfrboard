package com.project.cfrboard.controller;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.service.CfrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/cfrs")
public class CfrController {

    private final CfrService cfrService;

    @GetMapping("/form")
    public String cfrForm(@AuthenticationPrincipal PrincipalDetails principal,
                          Model model) {
        if (principal == null) {
            throw new AccessDeniedException("권한없음");
        }

        model.addAttribute("requestCount", cfrService.cfrRequestCount(principal.getMember().getId()));
        return "cfr/cfrForm";
    }

    @GetMapping
    public String list(@AuthenticationPrincipal PrincipalDetails principal,
                       @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {
        if (principal == null) {
            throw new AccessDeniedException("권한없음");
        }
        model.addAttribute("cfrDataList", cfrService.getCfrList(principal.getMember().getId(), cusPageable(pageable)));
        return "cfr/list";
    }

    private PageRequest cusPageable(Pageable pageable) {
        System.out.println(pageable.getPageSize());
        return PageRequest.of((pageable.getPageNumber()==0)?0:pageable.getPageNumber()-1, 15, Sort.by("createDate").descending());
    }

}
