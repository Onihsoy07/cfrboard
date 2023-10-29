package com.project.cfrboard.controller;

import com.project.cfrboard.domain.dto.MemberJoinDto;
import com.project.cfrboard.domain.dto.MemberLoginDto;
import com.project.cfrboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

        if (referer == null || !referer.contains(host)) {
            referer = "/";
            session.setAttribute("prePage", referer);
        } else if (referer.contains("/auth")) {
            if (session.getAttribute("prePage") == null) {
                referer = "/";
                session.setAttribute("prePage", referer);
            }
        } else {
            session.setAttribute("prePage", referer);
        }

        return "auth/loginForm";
    }

    @GetMapping("/register")
    public String joinForm(@ModelAttribute MemberJoinDto memberJoinDto) {
        return "auth/joinForm";
    }


}
