package com.project.cfrboard.controller;

import com.project.cfrboard.domain.dto.InquiryPageDto;
import com.project.cfrboard.service.BoardService;
import com.project.cfrboard.service.CfrService;
import com.project.cfrboard.service.InquiryService;
import com.project.cfrboard.service.PageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/inquirys")
public class InquiryController {

    private final InquiryService inquiryService;
    private final PageService pageService;
    private final BoardService boardService;
    private final CfrService cfrService;

    @GetMapping
    public String list(Model model,
                       @PageableDefault Pageable pageable) {
        int page = pageable.getPageNumber()==0?1:pageable.getPageNumber();
        Page<InquiryPageDto> inquiryList = inquiryService.getInquiryList(pageService.cusPageable(pageable));

        if (pageable.getPageNumber() >= 2 && inquiryList.getContent().size() <= 0) {
            return "redirect:/inquirys";
        }
        model.addAttribute("inquiryList", inquiryList);
        model.addAttribute("pageInfo", pageService.getPageOffset(pageable, inquiryList));
        model.addAttribute("topTodayView", boardService.getTodayTopView());
        model.addAttribute("topCfr", cfrService.getTopConfidenceCfrData());

        return "inquiry/list";
    }

    @GetMapping("/form")
    public String inquiryForm() {
        return "inquiry/writeForm";
    }

}
