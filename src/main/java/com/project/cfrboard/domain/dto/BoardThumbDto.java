package com.project.cfrboard.domain.dto;

import com.project.cfrboard.domain.entity.Board;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class BoardThumbDto {

    private Long id;
    private String title;
    private String boardTable;
    private int commentCount;

    public BoardThumbDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.boardTable = board.getBoardTable().getValue();
        this.commentCount = board.getCommentCount();
    }

    public static List<BoardThumbDto> convertToDtoList(Collection<Board> boardList) {
        return boardList.stream().map(BoardThumbDto::new).collect(Collectors.toList());
    }
}
