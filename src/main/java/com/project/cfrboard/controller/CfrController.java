package com.project.cfrboard.controller;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.dto.CfrDataDto;
import com.project.cfrboard.domain.dto.CfrResponseDto;
import com.project.cfrboard.exception.NotOnePeoplePhotoException;
import com.project.cfrboard.service.CfrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/cfr")
public class CfrController {

    private final CfrService cfrService;

    @GetMapping
    public String cfrForm() {
        return "cfr/cfrForm";
    }

    @GetMapping("/list")
    public String list(@AuthenticationPrincipal PrincipalDetails principal,
                       Model model) {
        if (principal == null) {
            throw new AccessDeniedException("권한없음");
        }
        model.addAttribute("cfrDataList", cfrService.getCfrList(principal.getMember()));
        return "cfr/list";
    }

}
