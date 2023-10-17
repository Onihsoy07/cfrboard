package com.project.cfrboard.controller;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.constant.MyConstant;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.project.cfrboard.domain.constant.MyConstant.*;

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
                       @RequestParam(value = "target", required = false) String target,
                       @RequestParam(value = "value", required = false) String value,
                       @PageableDefault Pageable pageable,
                       @AuthenticationPrincipal PrincipalDetails principal) {
        int page = pageable.getPageNumber()==0?1:pageable.getPageNumber();
        Page<InquiryPageDto> inquiryList;

        if (INQUIRY_TARGET.contains(target)) {
            if (target.equals("my")) {
                if ((principal != null) && principal.getUsername().equals(value)) {
                    inquiryList = inquiryService.getMyInquiryList(pageService.cusPageable(pageable), value);
                    model.addAttribute("status", "target=" + target + "&value=" + value + "&page=" + page);
                    model.addAttribute("nextPage", "target=" + target + "&value=" + value);
                } else {
                    return "redirect:/inquirys";
                }
            } else {
                inquiryList = inquiryService.getTargetInquiryList(pageService.cusPageable(pageable), target);
                model.addAttribute("status", "target=" + target + "&page=" + page);
                model.addAttribute("nextPage", "target=" + target);
            }
        } else {
            inquiryList = inquiryService.getInquiryList(pageService.cusPageable(pageable));
            model.addAttribute("status", "page=" + page);
        }

        if (pageable.getPageNumber() >= 2 && inquiryList.getContent().size() <= 0) {
            return "redirect:/inquirys";
        }
        model.addAttribute("inquiryList", inquiryList);
        model.addAttribute("pageInfo", pageService.getPageOffset(pageable, inquiryList));
        model.addAttribute("topTodayView", boardService.getTodayTopView());
        model.addAttribute("topCfr", cfrService.getTopConfidenceCfrData());

        return "inquiry/list";
    }

    @GetMapping("/{inquiryId}")
    public String inquiryDetail(@PathVariable("inquiryId") Long inquiryId,
                                Model model,
                                @RequestParam(value = "target", required = false) String target,
                                @RequestParam(value = "value", required = false) String value,
                                @AuthenticationPrincipal PrincipalDetails principal,
                                @PageableDefault Pageable pageable) {
        model.addAttribute("inquiryView", inquiryService.getInquiryDetail(inquiryId));

        int page = pageable.getPageNumber()==0?1:pageable.getPageNumber();
        Page<InquiryPageDto> inquiryList;

        if (INQUIRY_TARGET.contains(target)) {
            if (target.equals("my")) {
                if ((principal != null) && principal.getUsername().equals(value)) {
                    inquiryList = inquiryService.getMyInquiryList(pageService.cusPageable(pageable), value);
                    model.addAttribute("status", "target=" + target + "&value=" + value + "&page=" + page);
                    model.addAttribute("nextPage", "target=" + target + "&value=" + value);
                } else {
                    return "redirect:/inquirys";
                }
            } else {
                inquiryList = inquiryService.getTargetInquiryList(pageService.cusPageable(pageable), target);
                model.addAttribute("status", "target=" + target + "&page=" + page);
                model.addAttribute("nextPage", "target=" + target);
            }
        } else {
            inquiryList = inquiryService.getInquiryList(pageService.cusPageable(pageable));
            model.addAttribute("status", "page=" + page);
        }

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
