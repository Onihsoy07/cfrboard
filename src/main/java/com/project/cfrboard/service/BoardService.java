package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.BoardFormDto;
import com.project.cfrboard.domain.entity.Board;
import com.project.cfrboard.domain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardFormDto boardFormDto) {
        Board board = Board.builder()
                .title(boardFormDto.getTitle())
                .content(boardFormDto.getContent())
                .boardTable(boardFormDto.getBoardTable())
                .build();

        boardRepository.save(board);
    }
}
