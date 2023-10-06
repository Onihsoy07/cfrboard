package com.project.cfrboard.domain.dto;

import com.project.cfrboard.domain.entity.Member;
import com.project.cfrboard.domain.entity.enumeration.InquiryTarget;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class InquiryFormDto {

    @NotBlank(message = "제목를 입력해주세요.")
    private String title;
    @NotBlank(message = "내용를 입력해주세요.")
    private String content;
    private Boolean isSecret;
    @NotBlank(message = "문의 종류를 선택해주세요.")
    private String target;

}
