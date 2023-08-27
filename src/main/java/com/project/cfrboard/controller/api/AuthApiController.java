package com.project.cfrboard.controller.api;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.dto.MemberPasswordCheckDto;
import com.project.cfrboard.domain.repository.MemberRepository;
import com.project.cfrboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {

    private final MemberService memberService;

    @GetMapping("/ducheck")
    public ResponseEntity<Boolean> usernameDuplicateCheck(@RequestParam String username) {
        return ResponseEntity.ok(memberService.usernameDuplicateCheck(username));
    }

}