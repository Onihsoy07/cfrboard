package com.project.cfrboard.controller;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.dto.MemberModifyDto;
import com.project.cfrboard.domain.dto.MemberPasswordCheckDto;
import com.project.cfrboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}/confirm")
    public String confirm(@PathVariable Long memberId,
                          Model model) {
        model.addAttribute("memberId", memberId);
        return "member/confirm";
    }

    @GetMapping("/{memberId}/edit")
    public String detail(@PathVariable Long memberId,
                         @AuthenticationPrincipal PrincipalDetails principal,
                         @ModelAttribute MemberModifyDto memberModifyDto,
                         Model model) {
        model.addAttribute("memberId", memberId);
        String username = principal.getUsername();
        if (memberService.passwordCheckIsTrue(username)) {
            memberService.passwordCheckReset(username);
            memberModifyDto.setUsername(principal.getUsername());
            return "member/detail";
        } else {
            return "redirect:/members/" + memberId + "/confirm";
        }
    }
}
