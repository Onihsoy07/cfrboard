package com.project.cfrboard.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ReplyUpdateDto {

    @NotBlank(message = "댓글을 입력해주세요.")
    @Size(min = 3, message = "댓글을 3자 이상 써주세요.")
    private String comment;
}
