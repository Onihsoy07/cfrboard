package com.project.cfrboard.controller;

import com.project.cfrboard.domain.dto.BoardFormDto;
import com.project.cfrboard.domain.dto.BoardThumbDto;
import com.project.cfrboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/form")
    public String boardForm(@ModelAttribute BoardFormDto boardFormDto) {
        return "board/form";
    }

    @GetMapping
    public String boardMainPage(@RequestParam("bt") String boardTable,
                                @PageableDefault Pageable pageable,
                                Model model) {
        model.addAttribute("boardList", boardService.getBoardList(boardTable, cusPageable(pageable)));
        return "board/board";
    }

    private PageRequest cusPageable(Pageable pageable) {
        return PageRequest.of((pageable.getPageNumber()==0)?0:pageable.getPageNumber()-1, 15, Sort.by("createDate").descending());
    }
}
