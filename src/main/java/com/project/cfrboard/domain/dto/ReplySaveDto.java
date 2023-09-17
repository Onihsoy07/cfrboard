package com.project.cfrboard.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class ReplySaveDto {

    @NotBlank(message = "댓글을 입력해주세요.")
    @Size(min = 3, message = "댓글을 3자 이상 써주세요.")
    private String comment;
    @NotNull(message = "필요한 데이터가 없습니다.")
    @Min(value = 0, message = "데이터가 이상합니다.")
    private Long boardId;
    @NotNull(message = "필요한 데이터가 없습니다.")
    @Min(value = 0, message = "데이터가 이상합니다.")
    private Long replyId;
    @NotNull(message = "필요한 데이터가 없습니다.")
    @Min(value = 0, message = "데이터가 이상합니다.")
    @Max(value = 7, message = "데이터가 이상합니다.")
    private int depth;
}
