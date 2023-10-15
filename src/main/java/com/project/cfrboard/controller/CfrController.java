package com.project.cfrboard.controller;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.dto.CfrDataDto;
import com.project.cfrboard.service.CfrService;
import com.project.cfrboard.service.PageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    private final PageService pageService;

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
        Page<CfrDataDto> cfrList = cfrService.getCfrList(principal.getMember().getId(), pageService.cusPageable(pageable));

        model.addAttribute("cfrDataList", cfrList);
        model.addAttribute("pageInfo", pageService.getPageOffset(pageable, cfrList));
        return "cfr/list";
    }

}
