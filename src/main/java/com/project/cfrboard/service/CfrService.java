package com.project.cfrboard.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.cfrboard.domain.dto.CfrDataDto;
import com.project.cfrboard.domain.dto.CfrResponseDto;
import com.project.cfrboard.domain.entity.CfrData;
import com.project.cfrboard.domain.entity.Member;
import com.project.cfrboard.domain.repository.CfrDataRepository;
import com.project.cfrboard.exception.NotOnePeoplePhotoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CfrService {

    private final CfrDataRepository cfrDataRepository;
    private final ModelMapper modelMapper;

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

    @Transactional
    public void save(CfrResponseDto cfrResponseDto, Member member) {
        if (isOnePeople(cfrResponseDto)) {
            CfrData cfrData = CfrData.builder()
                    .value(cfrResponseDto.getFaces().get(0).celebrity.value)
                    .confidence(cfrResponseDto.getFaces().get(0).celebrity.confidence)
                    .member(member)
                    .build();
            cfrDataRepository.save(cfrData);
        } else {
            throw new NotOnePeoplePhotoException(String.format("사람의 수가 %d명 입니다.", cfrResponseDto.getFaces().size()));
        }
    }

    private Boolean isOnePeople(CfrResponseDto cfrResponseDto) {
        if (cfrResponseDto.getFaces().size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public Page<CfrDataDto> getCfrList(Long memberId, Pageable pageable) {
        return cfrDataRepository.findCfrDataDtoList(memberId, pageable);
    }

}
