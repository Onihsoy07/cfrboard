package com.project.cfrboard.controller.api;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.dto.CfrResponseDto;
import com.project.cfrboard.exception.NotOnePeoplePhotoException;
import com.project.cfrboard.service.CfrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cfrs")
public class CfrApiController {

    private final CfrService cfrService;

    @PostMapping
    public ResponseEntity<Boolean> saveCfrData(@RequestParam("image") MultipartFile multipartFile,
                                               @AuthenticationPrincipal PrincipalDetails principal) {
        CfrResponseDto cfrResponseDto = cfrService.getCfrResponseDto(multipartFile);
        try {
            cfrService.save(cfrResponseDto, principal.getMember());
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (NotOnePeoplePhotoException e) {
            log.error("사진의 사람의 수가 0 or 다수입니다.", e);
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
        //400 Bad Request: "{"errorCode":"ER04","errorMessage":"Image size is too large (너무 큰 이미지 입니다.)"}"
    }

}
