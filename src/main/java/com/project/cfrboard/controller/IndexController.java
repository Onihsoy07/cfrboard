package com.project.cfrboard.controller;

import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import com.project.cfrboard.service.BoardService;
import com.project.cfrboard.service.CfrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final BoardService boardService;
    private final CfrService cfrService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("currentFreeBoard", boardService.getCurrentBoard(BoardTable.FREE));
        model.addAttribute("currentCfrBoard", boardService.getCurrentBoard(BoardTable.CFR));
        model.addAttribute("topTodayView", boardService.getTodayTopView());
        model.addAttribute("topCfr", cfrService.getTopConfidenceCfrData());

        return "index";
    }
}
