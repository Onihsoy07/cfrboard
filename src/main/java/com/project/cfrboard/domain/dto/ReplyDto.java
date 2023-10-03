package com.project.cfrboard.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {

    private Long replyId;
    private Long parentReplyId;
    private String username;
    private String comment;
    private Integer depth;
    private Integer childCommentCount;
    private Boolean isBlinded;
    private LocalDateTime createDate;
}
