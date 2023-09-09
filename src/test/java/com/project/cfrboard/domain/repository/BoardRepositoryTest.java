package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.dto.BoardThumbDto;
import com.project.cfrboard.domain.entity.Board;
import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        PageImpl<BoardThumbDto> boards = new PageImpl<>(BoardThumbDto.convertToDtoList(boardList), page, boardRepository.countByBoardTable(boardTable));
        log.info("boards = {}", boards);
    }
}