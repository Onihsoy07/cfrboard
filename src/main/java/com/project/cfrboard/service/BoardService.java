package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.BoardDto;
import com.project.cfrboard.domain.dto.BoardFormDto;
import com.project.cfrboard.domain.dto.BoardThumbDto;
import com.project.cfrboard.domain.dto.BoardUpdateFormDto;
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
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final CfrDataRepository cfrDataRepository;

    public void save(BoardFormDto boardFormDto, Member member) throws NoBoardTableException, NotMatchMemberDataException, IllegalArgumentException {
        boolean isPresentBoard = EnumUtils.isValidEnumIgnoreCase(BoardTable.class, boardFormDto.getBoardTable());
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
            if (member.getId() != cfrData.getMember().getId()) {
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

    @Transactional(readOnly = true)
    public Page<BoardThumbDto> getBoardList(String boardTable, Pageable pageable) {
        BoardTable bt = BoardTable.valueOf(boardTable.toUpperCase());
        List<Board> boardList = boardRepository.findByBoardTable(bt, pageable);
        return new PageImpl<>(BoardThumbDto.convertToDtoList(boardList), pageable, boardRepository.countByBoardTable(bt));
    }

    @Transactional(readOnly = true)
    public BoardDto getBoardView(Long boardId) {
        Board board = getBoard(boardId);
        return new BoardDto(board);
    }

    @Transactional(readOnly = true)
    public BoardUpdateFormDto getUpdateBoardForm(Long boardId) {
        Board board = getBoard(boardId);
        return new BoardUpdateFormDto(board);
    }

    @Transactional(readOnly = true)
    public Boolean isBoardMaster(Long memberId, Long boardId) {
        return boardRepository.findByIdFetchMember(boardId).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("Board ID %d로 찾을 수 없습니다.", boardId));
        }).getMember().getId().equals(memberId);
    }

    private CfrData getCfrData(Long cfrId) {
        return cfrDataRepository.findById(cfrId).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("CfrData ID %d로 찾을 수 없습니다.", cfrId));
        });
    }

    private Board getBoard(Long boardId) {
        return boardRepository.findByIdFetchMemberAndCfrdata(boardId).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("Board ID %d로 찾을 수 없습니다.", boardId));
        });
    }
}
