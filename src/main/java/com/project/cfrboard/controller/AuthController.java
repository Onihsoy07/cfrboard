package com.project.cfrboard.controller;

import com.project.cfrboard.domain.dto.MemberJoinDto;
import com.project.cfrboard.domain.entity.Member;
import com.project.cfrboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;

    @GetMapping("/loginForm")
    public String loginForm(@ModelAttribute Member member) {
        return "auth/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(@ModelAttribute Member member) {
        return "auth/joinForm";
    }

    @PostMapping("/joinForm")
    public String join(@ModelAttribute MemberJoinDto memberJoinDto) {
        memberService.join(memberJoinDto);
        return "redirect:/";
    }
}
