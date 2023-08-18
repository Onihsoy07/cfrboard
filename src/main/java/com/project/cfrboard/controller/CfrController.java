package com.project.cfrboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/cfr")
public class CfrController {

    @GetMapping
    public String cfrForm() {
        return "cfr/cfrForm";
    }
}
