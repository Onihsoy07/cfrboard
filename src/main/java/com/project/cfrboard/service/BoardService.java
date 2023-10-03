package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.*;
import com.project.cfrboard.domain.entity.Board;
import com.project.cfrboard.domain.entity.CfrData;
import com.project.cfrboard.domain.entity.Member;
import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import com.project.cfrboard.domain.repository.BoardRepository;
import com.project.cfrboard.domain.repository.CfrDataRepository;
import com.project.cfrboard.domain.repository.query.BoardQueryRepository;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final CfrDataRepository cfrDataRepository;
    private final BoardQueryRepository boardQueryRepository;

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
    public Page<BoardPageDto> getBoardList(String boardTable, Pageable pageable) {
        BoardTable bt = BoardTable.valueOf(boardTable.toUpperCase());
        List<Board> boardList = boardRepository.findByBoardTable(bt, pageable);
        return new PageImpl<>(BoardPageDto.convertToDtoList(boardList), pageable, boardRepository.countByBoardTable(bt));
    }

    @Transactional(readOnly = true)
    public Page<BoardPageDto> getSearchBoardList(String boardTable, Pageable pageable, String target, String keyword) {
        BoardTable bt = BoardTable.valueOf(boardTable.toUpperCase());
        return boardQueryRepository.search(bt, pageable, target, keyword);
    }

    @Transactional(readOnly = true)
    public BoardDto getBoardView(Long boardId) {
        Board board = getBoardFetchMemberAndCfrdata(boardId);
        return new BoardDto(board);
    }

    @Transactional(readOnly = true)
    public BoardUpdateFormDto getUpdateBoardForm(Long boardId) {
        Board board = getBoardFetchMemberAndCfrdata(boardId);
        return new BoardUpdateFormDto(board);
    }

    @Transactional(readOnly = true)
    public Boolean isBoardMaster(Long memberId, Long boardId) {
        return boardRepository.findByIdFetchMember(boardId).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("Board ID %d로 찾을 수 없습니다.", boardId));
        }).getMember().getId().equals(memberId);
    }

    public void update(BoardUpdateFormDto boardUpdateFormDto,
                       Long boardId) {
        Board board = getBoard(boardId);
        board.update(boardUpdateFormDto.getTitle(), boardUpdateFormDto.getContent());
    }

    public void delete(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    @Transactional(readOnly = true)
    public List<BoardThumbDto> getCurrentBoard(BoardTable boardTable) {
        List<Board> currentBoardList = boardRepository.findTop10ByBoardTableAndIsBlindedOrderByCreateDateDesc(boardTable, false);
        return BoardThumbDto.convertToDtoList(currentBoardList);
    }

    @Transactional(readOnly = true)
    public List<BoardThumbDto> getTodayTopView() {
        List<Board> topViewList = boardRepository.findTop10ByTodayViewCountGreaterThanEqualAndIsBlindedOrderByTodayViewCountDescCreateDateDesc(1, false);
        return BoardThumbDto.convertToDtoList(topViewList);
    }


    private CfrData getCfrData(Long cfrId) {
        return cfrDataRepository.findById(cfrId).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("CfrData ID %d로 찾을 수 없습니다.", cfrId));
        });
    }

    private Board getBoardFetchMemberAndCfrdata(Long boardId) {
        return boardRepository.findByIdFetchMemberAndCfrdata(boardId).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("Board ID %d로 찾을 수 없습니다.", boardId));
        });
    }

    private Board getBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("Board ID %d로 찾을 수 없습니다.", boardId));
        });
    }

    
    //트랜잭션 사용안함 -> @Transactional에서 비활성화 추후 찾기
    public void viewCount(Long boardId, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Cookie thisCookie = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("viewBoardList".equals(cookie.getName())) {
                    thisCookie = cookie;
                    break;
                }
            }
        }

        if (thisCookie == null) {
            thisCookie = new Cookie("viewBoardList", String.format("_%d_", boardId));
            cookieSetAndViewCount(boardId, response, thisCookie);
        } else if (thisCookie.getValue().contains(String.format("_%d_", boardId))) {
        } else {
            thisCookie.setValue(thisCookie.getValue() + String.format("%d_", boardId));
            cookieSetAndViewCount(boardId, response, thisCookie);
        }
    }

    private void cookieSetAndViewCount(Long boardId, HttpServletResponse response, Cookie cookie) {
        cookie.setMaxAge(calcEndSec());
        cookie.setPath("/");
        response.addCookie(cookie);
        boardRepository.addViewCount(boardId);
    }

    private int calcEndSec() {
        // 현재 하루의 종료 시간, 2022-08-20T23:59:59.9999999
        LocalDateTime todayEndTime = LocalDate.now().atTime(LocalTime.MAX);

        // 현재 시간, 2022-08-20T19:39:10.936
        LocalDateTime currentTime = LocalDateTime.now();

        // 하루 종료 시간을 시간초로 변환
        long todayEndSecond = todayEndTime.toEpochSecond(ZoneOffset.UTC);

        // 현재 시간을 시간초로 변환
        long currentSecond = currentTime.toEpochSecond(ZoneOffset.UTC);

        // 하루 종료까지 남은 시간초
        long remainingTime = todayEndSecond - currentSecond;

        return Long.valueOf(remainingTime).intValue();
    }

}
