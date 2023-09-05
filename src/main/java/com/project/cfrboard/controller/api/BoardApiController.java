package com.project.cfrboard.controller.api;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.dto.BoardFormDto;
import com.project.cfrboard.domain.dto.ResponseDto;
import com.project.cfrboard.exception.NoBoardTableException;
import com.project.cfrboard.exception.NotMatchMemberDataException;
import com.project.cfrboard.service.BoardService;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<ResponseDto<?>> save(@Valid @RequestBody BoardFormDto boardFormDto,
                                               BindingResult bindingResult,
                                               @AuthenticationPrincipal PrincipalDetails principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>(false, null, "권한 없음"));
        }

        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, error.getDefaultMessage()));
        }

        try {
            boardService.save(boardFormDto, principal.getMember());
            return ResponseEntity.created(URI.create("/")).body(new ResponseDto<>(true, null, "저장 성공"));
        } catch (NoBoardTableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, "BoardTable이 잘못되었습니다."));
        } catch (NotMatchMemberDataException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto<>(false, null, "접근 권리 없음"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, "존재하지 않는 CFR DATA입니다."));
        }
    }
}
