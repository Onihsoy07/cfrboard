package com.project.cfrboard.controller.api;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.dto.LikeSaveInfoDto;
import com.project.cfrboard.domain.dto.ResponseDto;
import com.project.cfrboard.domain.repository.LikesRepository;
import com.project.cfrboard.service.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikesApiController {

    private final LikesService likesService;

    @PostMapping
    public ResponseEntity<ResponseDto<?>> save(@RequestBody final LikeSaveInfoDto likeSaveInfoDto,
                                               @AuthenticationPrincipal PrincipalDetails principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>(false, null, "권한 없음"));
        }
        if (likeSaveInfoDto.getMemberId() == null ||  likeSaveInfoDto.getBoardId() == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, "필요한 데이터가 없습니다."));
        }
        if (!principal.getMember().getId().equals(likeSaveInfoDto.getMemberId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto<>(false, null, "권한 없음"));
        }
        if (likesService.isBoardLikes(likeSaveInfoDto.getMemberId(), likeSaveInfoDto.getBoardId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, "좋아요는 한번만 가능합니다."));
        }
        likesService.save(likeSaveInfoDto.getMemberId(), likeSaveInfoDto.getBoardId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(true, null, "좋아요를 하였습니다."));
    }
}
