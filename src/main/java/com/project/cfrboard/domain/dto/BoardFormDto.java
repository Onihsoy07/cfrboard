package com.project.cfrboard.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class BoardFormDto {

    @NotBlank(message = "제목를 입력하시오.")
    @Min(value = 5, message = "제목을 5자 이상 쓰시오.")
    private String title;

    @NotBlank(message = "내용를 입력하시오.")
    @Min(value = 10, message = "내용을 10자 이상 쓰시오.")
    private String content;

}
