package com.project.cfrboard.controller.api;

import com.project.cfrboard.auth.PrincipalDetails;
import com.project.cfrboard.domain.dto.CfrResponseDto;
import com.project.cfrboard.domain.dto.ResponseDto;
import com.project.cfrboard.exception.NotOnePeoplePhotoException;
import com.project.cfrboard.service.CfrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cfrs")
public class CfrApiController {

    private final CfrService cfrService;

    @PostMapping
    public ResponseEntity<ResponseDto<Boolean>> saveCfrData(@RequestParam("image") MultipartFile multipartFile,
                                                            @AuthenticationPrincipal PrincipalDetails principal,
                                                            HttpServletResponse httpServletResponse) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>(false, null, "권한 없음"));
        }

        String cfrLimitMessage = cfrService.cfrLimitCheck(principal.getMember().getId());
        if (!"가능".equals(cfrLimitMessage)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, cfrLimitMessage));
        }

        CfrResponseDto cfrResponseDto = cfrService.getCfrResponseDto(multipartFile);

        try {
            cfrService.save(cfrResponseDto, principal.getMember());
            httpServletResponse.setHeader("Location", "/cfrs");
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(true, true, null));
        } catch (NotOnePeoplePhotoException e) {
            log.error("사진의 사람의 수가 0 or 다수입니다.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, "사진의 사람의 수가 0 or 다수입니다."));
        } catch (HttpClientErrorException e) {
            String msg = e.getLocalizedMessage();
            Integer startMsg = msg.indexOf("(") + 1;
            Integer endMsg = msg.indexOf(")");
            msg = msg.substring(startMsg, endMsg);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, null, msg));
        }
    }

}
