package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.BoardFormDto;
import com.project.cfrboard.domain.entity.Board;
import com.project.cfrboard.domain.entity.CfrData;
import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import com.project.cfrboard.domain.repository.BoardRepository;
import com.project.cfrboard.domain.repository.CfrDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CfrDataRepository cfrDataRepository;

    public void save(BoardFormDto boardFormDto) throws IllegalArgumentException {
        boolean isPresentBoard = Arrays.stream(BoardTable.values()).anyMatch(bt -> bt.getValue().equals(boardFormDto.getBoardTable()));
        Board board = null;

        if (!isPresentBoard) {
            throw new IllegalArgumentException(String.format("BoardTable %s가 존재하지 않습니다.", boardFormDto.getBoardTable()));
        }

        if (BoardTable.FREE.equals(boardFormDto.getBoardTable())) {
            board = Board.builder()
                    .title(boardFormDto.getTitle())
                    .content(boardFormDto.getContent())
                    .boardTable(BoardTable.valueOf(boardFormDto.getBoardTable()))
                    .cfrData(null)
                    .build();
        } else {
            CfrData cfrData = getCfrData(boardFormDto.getCfrId());

            board = Board.builder()
                    .title(boardFormDto.getTitle())
                    .content(boardFormDto.getContent())
                    .boardTable(BoardTable.valueOf(boardFormDto.getBoardTable()))
                    .cfrData(cfrData)
                    .build();
        }

        boardRepository.save(board);
    }

    private CfrData getCfrData(Long id) {
        return cfrDataRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("CfrData ID %d로 찾을 수 없습니다.", id));
        });
    }
}
