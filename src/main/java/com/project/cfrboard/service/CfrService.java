package com.project.cfrboard.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.cfrboard.domain.dto.CfrResponseDto;
import com.project.cfrboard.domain.repository.CfrDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class CfrService {

    private final CfrDataRepository cfrDataRepository;

    @Value("${open_api.naver.Client_ID}")
    private String X_Naver_Client_Id;

    @Value("${open_api.naver.Client_Secret}")
    private String X_Naver_Client_Secret;

    public CfrResponseDto getCfrResponseDto(MultipartFile multipartFile) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.add("X-Naver-Client-Id", X_Naver_Client_Id);
        httpHeaders.add("X-Naver-Client-Secret", X_Naver_Client_Secret);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", multipartFile.getResource());

        HttpEntity<MultiValueMap<String, Object>> CFRRequest =
                new HttpEntity<>(body, httpHeaders);

        ResponseEntity<String> response = rt.exchange(
                "https://openapi.naver.com/v1/vision/celebrity",
                HttpMethod.POST,
                CFRRequest,
                String.class
        );

        ObjectMapper mapper = new ObjectMapper();
        CfrResponseDto cfrResponseDto;
        try {
            cfrResponseDto = mapper.readValue(response.getBody(), CfrResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return cfrResponseDto;
    }
}
