package com.project.cfrboard.domain.dto;

import com.project.cfrboard.domain.entity.Board;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class BoardPageDto {

    private Long id;
    private String title;
    private LocalDateTime createDate;
    private Integer viewCount;
    private Integer likesCount;
    private Boolean isBlinded;
    private MemberDto memberDto;

    public BoardPageDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.createDate = board.getCreateDate();
        this.viewCount = board.getTotalViewCount();
        this.likesCount = board.getLikesCount();
        this.isBlinded = board.getIsBlinded();
        this.memberDto = new MemberDto(board.getMember());
    }

    public static List<BoardPageDto> convertToDtoList(Collection<Board> boardList) {
        return boardList.stream().map(BoardPageDto::new).collect(Collectors.toList());
    }

}
