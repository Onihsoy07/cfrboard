package com.project.cfrboard.controller.api;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.dto.ReplySaveDto;
import com.project.cfrboard.domain.dto.ResponseDto;
import com.project.cfrboard.exception.NotMasterOfDataException;
import com.project.cfrboard.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/replys")
public class ReplyApiController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<ResponseDto<?>> save(@RequestBody @Valid final ReplySaveDto replySaveDto,
                                               BindingResult bindingResult,
                                               @AuthenticationPrincipal PrincipalDetails principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>(false, null, "권한 없음"));
        }

        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, error.getDefaultMessage()));
        }

        replyService.save(replySaveDto, principal.getMember().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(true, null, "성공"));
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<ResponseDto<?>> delete(@PathVariable("replyId") Long replyId,
                                                 @AuthenticationPrincipal PrincipalDetails principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>(false, null, "권한 없음"));
        }

        try {
            replyService.delete(replyId, principal.getMember().getId());
        } catch (NotMasterOfDataException e) {
            log.info("댓글의 주인이 아닙니다.", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto<>(false, null, "댓글 삭제 권한이 없습니다."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(true, null, "성공"));
    }

}
