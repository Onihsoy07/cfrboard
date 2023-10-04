package com.project.cfrboard.controller;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.dto.BoardFormDto;
import com.project.cfrboard.domain.dto.BoardPageDto;
import com.project.cfrboard.domain.dto.PageDto;
import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import com.project.cfrboard.service.BoardService;
import com.project.cfrboard.service.CfrService;
import com.project.cfrboard.service.ReplyService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final CfrService cfrService;
    private final ReplyService replyService;

    private final List<String> TARGETLIST = Arrays.asList("all", "title-content", "title", "content", "username");

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
                                @RequestParam(value = "target", required = false) String target,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                Model model) {
        if (!EnumUtils.isValidEnumIgnoreCase(BoardTable.class, boardTable)) {
            return "redirect:/boards/free";
        }

        model.addAttribute("boardTable", boardTable);

        Page<BoardPageDto> boardList = null;
        int page = pageable.getPageNumber()==0?1:pageable.getPageNumber();

        if (target != null) {
            if (!TARGETLIST.contains(target)) {
                return "redirect:/boards/" + boardTable;
            }

            boardList = boardService.getSearchBoardList(boardTable, cusPageable(pageable), target, keyword);
            model.addAttribute("target", target);
            model.addAttribute("keyword", keyword);
            model.addAttribute("status", "target=" + target + "&keyword=" + keyword + "&page=" + page);
            model.addAttribute("nextPage", "target=" + target + "&keyword=" + keyword);

        } else {
            boardList = boardService.getBoardList(boardTable, cusPageable(pageable));
            model.addAttribute("status", "page=" + page);
        }

        if (pageable.getPageNumber() >= 2 && boardList.getContent().size() <= 0) {
            return "redirect:/boards/" + boardTable;
        }
        model.addAttribute("boardList", boardList);
        model.addAttribute("pageInfo", getPageOffset(pageable, boardList));
        model.addAttribute("topTodayView", boardService.getTodayTopView());
        model.addAttribute("topCfr", cfrService.getTopConfidenceCfrData());

        return "board/board";
    }

    @GetMapping("/{boardTable}/{boardId}")
    public String boardView(@PathVariable("boardTable") String boardTable,
                            @PathVariable("boardId") Long boardId,
                            @PageableDefault Pageable pageable,
                            @RequestParam(value = "target", required = false) String target,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            Model model,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        model.addAttribute("boardTable", boardTable);

        if (boardService.isBlinded(boardId)) {
            return "redirect:/boards/" + boardTable;
        }

        boardService.viewCount(boardId, request, response);

        Page<BoardPageDto> boardList = null;
        int page = pageable.getPageNumber()==0 ? 1 : pageable.getPageNumber();

        if (target != null) {
            if (!TARGETLIST.contains(target)) {
                return "redirect:/boards/" + boardTable;
            }

            boardList = boardService.getSearchBoardList(boardTable, cusPageable(pageable), target, keyword);
            model.addAttribute("target", target);
            model.addAttribute("keyword", keyword);
            model.addAttribute("status", "target=" + target + "&keyword=" + keyword + "&page=" + page);
            model.addAttribute("nextPage", "target=" + target + "&keyword=" + keyword);

        } else {
            boardList = boardService.getBoardList(boardTable, cusPageable(pageable));
            model.addAttribute("status", "page=" + page);
        }

        if (pageable.getPageNumber() >= 2 && boardList.getContent().size() <= 0) {
            return "redirect:/boards/" + boardTable;
        }
        model.addAttribute("boardList", boardList);
        model.addAttribute("pageInfo", getPageOffset(pageable, boardList));

        model.addAttribute("boardView", boardService.getBoardView(boardId));
        model.addAttribute("replyList", replyService.sortReply(boardId));
        model.addAttribute("topTodayView", boardService.getTodayTopView());
        model.addAttribute("topCfr", cfrService.getTopConfidenceCfrData());

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
        if (boardService.isBlinded(boardId)) {
            return "redirect:/";
        }

        model.addAttribute("boardUpdateForm", boardService.getUpdateBoardForm(boardId));

        return "board/updateForm";
    }

    private PageRequest cusPageable(Pageable pageable) {
        return PageRequest.of((pageable.getPageNumber()==0)?0:pageable.getPageNumber()-1, 15, Sort.by("createDate").descending());
    }

    private PageDto getPageOffset(Pageable pageable, Page<?> pageList) {
        int startPage = ((pageable.getPageNumber()-1)/10)*10;
        int endPage = pageList.getTotalPages()<=10 ? pageList.getTotalPages() : (startPage + 11);
        Boolean isPreviousPage = startPage==0 ? false : true;
        Boolean isNextPage = true;

        if (pageList.getTotalPages() <= endPage) {
            endPage = pageList.getTotalPages()+1;
            isNextPage = false;
        }

        return new PageDto(startPage, endPage, isPreviousPage, isNextPage);
    }
}
