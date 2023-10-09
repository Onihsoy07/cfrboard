package com.project.cfrboard.controller.api;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.dto.InquiryFormDto;
import com.project.cfrboard.domain.dto.ResponseDto;
import com.project.cfrboard.service.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.project.cfrboard.domain.constant.MyConstant.MANAGER_ROLE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/inquirys")
public class InquiryApiController {

    private final InquiryService inquiryService;

    @PostMapping
    public ResponseEntity<ResponseDto<?>> save(@Valid @RequestBody final InquiryFormDto inquiryFormDto,
                                               BindingResult bindingResult,
                                               @AuthenticationPrincipal PrincipalDetails principal) {
        log.info("InquiryFormDto = {}", inquiryFormDto);
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>(false, null, "권한 없음"));
        }

        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, error.getDefaultMessage()));
        }

        inquiryService.save(inquiryFormDto, principal.getMember());
        return ResponseEntity.created(URI.create("/")).body(new ResponseDto<>(true, null, "저장 성공"));
    }

    @PutMapping("/complete/{inquiryId}")
    public ResponseEntity<ResponseDto<?>> blind(@PathVariable("inquiryId") Long inquiryId,
                                                @AuthenticationPrincipal PrincipalDetails principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>(false, null, "권한 없음"));
        }

        if (MANAGER_ROLE.contains(principal.getMember().getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto<>(false, null, "권한 없음"));
        }

        inquiryService.complete(inquiryId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(true, null, "문의 완료 처리 완료"));
    }
}
