package com.project.cfrboard.domain.dto;

import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class BoardFormDto {

    @NotBlank(message = "제목를 입력해주세요.")
    @Size(min = 5, message = "제목을 5자 이상 입력해주세요.")
    @Size(max = 100, message = "제목을 100자 이하로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용를 입력해주세요.")
    @Size(min = 10, message = "내용을 10자 이상 입력해주세요.")
    private String content;

    private String boardTable;

    private Long cfrId;

}
