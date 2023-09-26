package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.dto.BoardPageDto;
import com.project.cfrboard.domain.entity.Board;
import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Slf4j
@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void findByBoardTableTest() {
        PageRequest page = PageRequest.of(0, 2);
        BoardTable boardTable = BoardTable.valueOf("FREE");
        List<Board> boardList = boardRepository.findByBoardTable(boardTable, page);
        PageImpl<BoardPageDto> boards = new PageImpl<>(BoardPageDto.convertToDtoList(boardList), page, boardRepository.countByBoardTable(boardTable));
        log.info("boards = {}", boards);
    }
}