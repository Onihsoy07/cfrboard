package com.project.cfrboard.domain.dto;

import com.project.cfrboard.domain.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private Integer viewCount;
    private Integer likesCount;
    private Integer commentCount;
    private Boolean isBlinded;
    private MemberDto memberDto;
    private CfrDataDto cfrDataDto;

    public BoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createDate = board.getCreateDate();
        this.viewCount = board.getTotalViewCount();
        this.likesCount = board.getLikesCount();
        this.commentCount = board.getCommentCount();
        this.isBlinded = board.getIsBlinded();
        this.memberDto = new MemberDto(board.getMember());
        this.cfrDataDto = board.getCfrData()==null ? null : new CfrDataDto(board.getCfrData());
    }
}
