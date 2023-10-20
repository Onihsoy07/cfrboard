package com.project.cfrboard.controller.api;

import com.project.cfrboard.domain.dto.MemberJoinDto;
import com.project.cfrboard.domain.dto.ResponseDto;
import com.project.cfrboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {

    private final MemberService memberService;

    @GetMapping("/duplicate-check")
    public ResponseEntity<ResponseDto<Boolean>> usernameDuplicateCheck(@RequestParam String username) {
        if (!username.matches("^(?=.*[a-zA-Z])(?=.*[0-9]).{0,999}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, "아이디는 영어와 숫자의 조합을 사용해주세요."));
        }
        if (!(4 <= username.length() && username.length() <= 10)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, "아이디는 4~10자리로 해주세요."));
        }
        Boolean isDuplicate = memberService.usernameDuplicateCheck(username);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(true, isDuplicate, null));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<?>> join(@Valid @RequestBody MemberJoinDto memberJoinDto,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, error.getDefaultMessage()));
        }

        if (!memberJoinDto.getPassword().equals(memberJoinDto.getPasswordCheck())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, "비밀번호가 다릅니다."));
        }

        memberService.join(memberJoinDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(true, null, "회원가입 성공"));
    }

}