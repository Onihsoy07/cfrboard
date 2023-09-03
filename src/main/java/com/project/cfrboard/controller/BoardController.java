package com.project.cfrboard.controller;

import com.project.cfrboard.domain.dto.BoardFormDto;
import com.project.cfrboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/form")
    public String boardForm(@ModelAttribute BoardFormDto boardFormDto) {
        return "/board/form";
    }
}
