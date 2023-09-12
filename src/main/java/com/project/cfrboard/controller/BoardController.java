package com.project.cfrboard.controller;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.dto.BoardFormDto;
import com.project.cfrboard.domain.dto.BoardThumbDto;
import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import com.project.cfrboard.service.BoardService;
import com.project.cfrboard.service.CfrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final CfrService cfrService;

    @GetMapping("/form")
    public String boardForm(@ModelAttribute BoardFormDto boardFormDto,
                            Model model,
                            @AuthenticationPrincipal PrincipalDetails principal) {
        model.addAttribute("cfrList", cfrService.getCfrList(principal.getMember().getId()));
        return "board/form";
    }

    @GetMapping("/{boardTable}")
    public String boardMainPage(@PathVariable("boardTable") String boardTable,
                                @PageableDefault Pageable pageable,
                                Model model) {
        if (!EnumUtils.isValidEnumIgnoreCase(BoardTable.class, boardTable)) {
            return "redirect:/boards/free";
        }

        model.addAttribute("boardTable", boardTable);

        Page<BoardThumbDto> boardList = boardService.getBoardList(boardTable, cusPageable(pageable));
        if (pageable.getPageNumber() >= 2 && boardList.getContent().size() <= 0) {
            return "redirect:/boards/" + boardTable;
        }
        model.addAttribute("boardList", boardList);

        return "board/board";
    }

    @GetMapping("/{boardTable}/{boardId}")
    public String boardView(@PathVariable("boardTable") String boardTable,
                            @PathVariable("boardId") Long boardId,
                            @PageableDefault Pageable pageable,
                            Model model) {
        model.addAttribute("boardTable", boardTable);

        Page<BoardThumbDto> boardList = boardService.getBoardList(boardTable, cusPageable(pageable));
        if (pageable.getPageNumber() >= 2 && boardList.getContent().size() <= 0) {
            return "redirect:/boards/" + boardTable;
        }
        model.addAttribute("boardList", boardList);

        model.addAttribute("boardView", boardService.getBoardView(boardId));

        return "board/board";
    }

    @GetMapping("/{boardId}/update-form")
    public String updateBoard(@PathVariable("boardId") Long boardId,
                              @AuthenticationPrincipal PrincipalDetails principal,
                              Model model) {
        if (boardId == null || principal == null) {
            return "redirect:/";
        }
        if (!boardService.isBoardMaster(principal.getMember().getId(), boardId)) {
            return "redirect:/";
        }

        model.addAttribute("boardUpdateForm", boardService.getUpdateBoardForm(boardId));

        return "board/updateForm";
    }

    private PageRequest cusPageable(Pageable pageable) {
        return PageRequest.of((pageable.getPageNumber()==0)?0:pageable.getPageNumber()-1, 15, Sort.by("createDate").descending());
    }
}
