package com.project.cfrboard.controller.api;

import com.project.cfrboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {

    private final MemberService memberService;

    @GetMapping("/duplicate-check")
    public ResponseEntity<Boolean> usernameDuplicateCheck(@RequestParam String username) {
        return ResponseEntity.ok(memberService.usernameDuplicateCheck(username));
    }

}