package com.project.cfrboard.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikeSaveInfoDto {

    private Long memberId;
    private Long boardId;
}
