package com.project.cfrboard.domain.dto;

import com.project.cfrboard.domain.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class BoardThumbDto {

    private Long id;
    private String title;
    private LocalDateTime createDate;
    private Integer viewCount;
    private Integer likesCount;
    private MemberDto memberDto;

    public BoardThumbDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.createDate = board.getCreateDate();
        this.viewCount = board.getViewCount();
        this.likesCount = board.getLikesCount();
        this.memberDto = new MemberDto(board.getMember());
    }

    public static List<BoardThumbDto> convertToDtoList(Collection<Board> boardList) {
        return boardList.stream().map(BoardThumbDto::new).collect(Collectors.toList());
    }

}
