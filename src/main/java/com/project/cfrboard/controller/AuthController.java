package com.project.cfrboard.controller;

import com.project.cfrboard.domain.dto.MemberJoinDto;
import com.project.cfrboard.domain.dto.MemberLoginDto;
import com.project.cfrboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute MemberLoginDto memberLoginDto,
                            HttpServletRequest request) {
        HttpSession session = request.getSession();
        String referer = request.getHeader("Referer");
        String host = request.getHeader("Host");

        if (session.getAttribute("prePage") == null) {
            if (referer == null || referer.contains("/auth") || !referer.contains(host)) {
                referer = "/";
            }
            session.setAttribute("prePage", referer);
        }

        return "auth/loginForm";
    }

    @GetMapping("/register")
    public String joinForm(@ModelAttribute MemberJoinDto memberJoinDto) {
        return "auth/joinForm";
    }


}
