package com.project.cfrboard.controller.api;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.dto.ReplySaveDto;
import com.project.cfrboard.domain.dto.ReplyUpdateDto;
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

import static com.project.cfrboard.domain.constant.MyConstant.MANAGER_ROLE;

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

        if (!replyService.deletableCheck(replyId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, "답글이 있어 삭제가 불가능합니다."));
        }

        try {
            replyService.delete(replyId, principal.getMember().getId());
        } catch (NotMasterOfDataException e) {
            log.info("댓글의 주인이 아닙니다.", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto<>(false, null, "댓글 삭제 권한이 없습니다."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(true, null, "성공"));
    }

    @PutMapping("/{replyId}")
    public ResponseEntity<ResponseDto<?>> update(@PathVariable("replyId") Long replyId,
                                                 @RequestBody @Valid final ReplyUpdateDto replyUpdateDto,
                                                 BindingResult bindingResult,
                                                 @AuthenticationPrincipal PrincipalDetails principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>(false, null, "권한 없음"));
        }

        if (replyService.isBlinded(replyId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, "블라인드 처리 된 댓글입니다."));
        }

        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, error.getDefaultMessage()));
        }

        try {
            replyService.update(replyUpdateDto.getComment(), replyId, principal.getMember().getId());
        } catch (NotMasterOfDataException e) {
            log.info("댓글의 주인이 아닙니다.", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto<>(false, null, "댓글 수정 권한이 없습니다."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(true, null, "성공"));
    }

    @PutMapping("/blind/{replyId}")
    public ResponseEntity<ResponseDto<?>> blind(@PathVariable("replyId") Long replyId,
                                                @AuthenticationPrincipal PrincipalDetails principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>(false, null, "권한 없음"));
        }

        if (MANAGER_ROLE.contains(principal.getMember().getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto<>(false, null, "권한 없음"));
        }

        replyService.blind(replyId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(true, null, "블라인드 처리 완료"));
    }
}
