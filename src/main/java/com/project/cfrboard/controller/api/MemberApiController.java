package com.project.cfrboard.controller.api;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.dto.MemberPasswordCheckDto;
import com.project.cfrboard.domain.dto.MemberUpdateDto;
import com.project.cfrboard.domain.dto.ResponseDto;
import com.project.cfrboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberApiController {

    private final MemberService memberService;

    @PutMapping("/{memberId}/confirm")
    public ResponseEntity<ResponseDto<?>> confirmCheck(@PathVariable Long memberId,
                                                       @Valid @ModelAttribute MemberPasswordCheckDto passwordCheckDto,
                                                       BindingResult bindingResult,
                                                       @AuthenticationPrincipal PrincipalDetails principal,
                                                       HttpServletResponse httpServletResponse) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>(false, null, "권한 없음"));
        }
        
        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, error.getDefaultMessage()));
        }

        if (memberCheck(memberId, principal)) {
            if (!(memberService.passwordCheck(passwordCheckDto, principal.getPassword()))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, "비밀번호가 다릅니다."));
            } else {
                memberService.passwordCheckComplete(principal.getUsername());

                return ResponseEntity.created(URI.create("/members/"+memberId+"/edit")).body(new ResponseDto<>(true, null, "사용자 확인 완료"));
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto<>(false, null, "요청 사용자 불일치"));
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<ResponseDto<?>> updateMember(@PathVariable Long memberId,
                                                       @Valid @RequestBody MemberUpdateDto updateDto,
                                                       BindingResult bindingResult,
                                                       @AuthenticationPrincipal PrincipalDetails principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>(false, null, "권한 없음"));
        }

        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, error.getDefaultMessage()));
        }

        if (memberCheck(memberId, principal)) {
            String result = memberService.update(updateDto, principal.getUsername());
            if ("ok".equals(result)) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(true, null, result));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(false, null, result));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto<>(false, null, "요청 사용자 불일치"));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ResponseDto<?>> deleteMember(@PathVariable Long memberId,
                                                       @AuthenticationPrincipal PrincipalDetails principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>(false, null, "권한 없음"));
        }

        if (memberCheck(memberId, principal)) {
            memberService.delete(memberId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(true, null, "삭제 완료"));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto<>(false, null, "요청 사용자 불일치"));
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
