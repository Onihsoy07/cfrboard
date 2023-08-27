package com.project.cfrboard.controller;

import com.project.cfrboard.domain.dto.MemberPasswordCheckDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/confirm")
    public String confirm(@ModelAttribute MemberPasswordCheckDto passwordCheckDto) {
        return "auth/confirm";
    }
}
