package com.project.cfrboard.controller.api;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.dto.MemberPasswordCheckDto;
import com.project.cfrboard.domain.dto.MemberUpdateDto;
import com.project.cfrboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/confirm")
    public ResponseEntity<Boolean> confirmCheck(@ModelAttribute MemberPasswordCheckDto passwordCheckDto,
                                                @AuthenticationPrincipal PrincipalDetails principal) {
        if (passwordCheckDto.getPassword() == null || principal == null || !(memberService.passwordCheck(passwordCheckDto, principal.getPassword()))) {
            return ResponseEntity.ok(false);
        } else {
            memberService.passwordCheckComplete(principal.getUsername());
            return ResponseEntity.ok(true);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateMember(@RequestBody MemberUpdateDto updateDto,
                                               @AuthenticationPrincipal PrincipalDetails principal) {
        String result = memberService.update(updateDto, principal.getUsername());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteMember(@RequestParam("id") String username,
                                                @AuthenticationPrincipal PrincipalDetails principal) {
        if (principal == null || username == null || !principal.getUsername().equals(username)) {
            return ResponseEntity.ok(false);
        } else {
            memberService.delete(username);
            return ResponseEntity.ok(true);
        }
    }

}
