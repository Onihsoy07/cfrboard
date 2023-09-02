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
@RequestMapping("/members")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/{memberId}/confirm")
    public ResponseEntity<Boolean> confirmCheck(@PathVariable Long memberId,
                                                @ModelAttribute MemberPasswordCheckDto passwordCheckDto,
                                                @AuthenticationPrincipal PrincipalDetails principal) {
        if (memberCheck(memberId, principal)) {
            if (passwordCheckDto.getPassword() == null || principal == null || !(memberService.passwordCheck(passwordCheckDto, principal.getPassword()))) {
                return ResponseEntity.ok(false);
            } else {
                memberService.passwordCheckComplete(principal.getUsername());
                return ResponseEntity.ok(true);
            }
        }

        return ResponseEntity.ok(false);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<String> updateMember(@PathVariable Long memberId,
                                               @RequestBody MemberUpdateDto updateDto,
                                               @AuthenticationPrincipal PrincipalDetails principal) {
        if (memberCheck(memberId, principal)) {
            String result = memberService.update(updateDto, principal.getUsername());
            return ResponseEntity.ok(result);
        }
        String result = memberService.update(updateDto, principal.getUsername());
        return ResponseEntity.ok("false");
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Boolean> deleteMember(@PathVariable Long memberId,
                                                @AuthenticationPrincipal PrincipalDetails principal) {
        if (memberCheck(memberId, principal)) {
            memberService.delete(memberId);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    private Boolean memberCheck(Long memberId, PrincipalDetails principal) {
        if (principal == null || memberId == null) {
            return false;
        }

        if (!memberId.equals(principal.getMember().getId())) {
            return false;
        }

        return true;
    }

}
