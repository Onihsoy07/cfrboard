package com.project.cfrboard.controller;

import com.project.cfrboard.domain.dto.MemberJoinDto;
import com.project.cfrboard.domain.dto.MemberLoginDto;
import com.project.cfrboard.domain.dto.MemberPasswordCheck;
import com.project.cfrboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping("/register")
    public String join(@Valid @ModelAttribute MemberJoinDto memberJoinDto,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                log.info("LoginForm Binding Error {} = {}", error.getObjectName(), error.getDefaultMessage());
            }
            return "auth/joinForm";
        }

        if (!memberJoinDto.getPassword().equals(memberJoinDto.getPasswordCheck())) {
            bindingResult.addError(new ObjectError("memberJoinDto", "비밀번호가 다릅니다."));
            for (ObjectError error : bindingResult.getAllErrors()) {
                log.info("LoginForm Binding Error {} = {}", error.getObjectName(), error.getDefaultMessage());
            }
            return "auth/joinForm";
        }
        memberService.join(memberJoinDto);
        return "redirect:/";
    }

    @GetMapping("/confirm")
    public String confirm(@ModelAttribute MemberPasswordCheck memberPasswordCheck) {
        return "auth/confirm";
    }

}
