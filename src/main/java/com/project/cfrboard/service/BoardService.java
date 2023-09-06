package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.BoardDto;
import com.project.cfrboard.domain.dto.BoardFormDto;
import com.project.cfrboard.domain.entity.Board;
import com.project.cfrboard.domain.entity.CfrData;
import com.project.cfrboard.domain.entity.Member;
import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import com.project.cfrboard.domain.repository.BoardRepository;
import com.project.cfrboard.domain.repository.CfrDataRepository;
import com.project.cfrboard.exception.NoBoardTableException;
import com.project.cfrboard.exception.NotMatchMemberDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final CfrDataRepository cfrDataRepository;

    public void save(BoardFormDto boardFormDto, Member member) throws NoBoardTableException, NotMatchMemberDataException, IllegalArgumentException {
        boolean isPresentBoard = Arrays.stream(BoardTable.values()).anyMatch(bt -> bt.getValue().equals(boardFormDto.getBoardTable()));
        Board board = null;

        if (!isPresentBoard) {
            throw new NoBoardTableException(String.format("BoardTable %s가 존재하지 않습니다.", boardFormDto.getBoardTable()));
        }

        if (BoardTable.FREE.getValue().equals(boardFormDto.getBoardTable())) {
            board = Board.builder()
                    .title(boardFormDto.getTitle())
                    .content(boardFormDto.getContent())
                    .boardTable(BoardTable.FREE)
                    .member(member)
                    .cfrData(null)
                    .build();
        } else {
            if (boardFormDto.getCfrId() == null) {
                throw new IllegalArgumentException("CfrData ID가 존재하지 않습니다.");
            }

            CfrData cfrData = getCfrData(boardFormDto.getCfrId());
            if (member != cfrData.getMember()) {
                throw new NotMatchMemberDataException("Member의 CfrData가 아닙니다.");
            }

            board = Board.builder()
                    .title(boardFormDto.getTitle())
                    .content(boardFormDto.getContent())
                    .boardTable(BoardTable.CFR)
                    .member(member)
                    .cfrData(cfrData)
                    .build();
        }

        boardRepository.save(board);
    }

    public Page<BoardDto> getBoardList(String boardTable) {
//        boardRepository.
    }

    private CfrData getCfrData(Long id) {
        return cfrDataRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("CfrData ID %d로 찾을 수 없습니다.", id));
        });
    }
}
