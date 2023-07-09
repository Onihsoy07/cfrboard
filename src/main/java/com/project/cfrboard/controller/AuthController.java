package com.project.cfrboard.controller;

import com.project.cfrboard.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/loginForm")
    public String loginForm(@ModelAttribute Member member) {
        return "auth/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(@ModelAttribute Member member) {
        return "auth/joinForm";
    }
}
