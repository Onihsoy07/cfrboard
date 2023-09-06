package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.dto.BoardDto;
import com.project.cfrboard.domain.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {


    @Query("select new com.project.cfrboard.domain.dto.BoardDto(b.title, b.content, b.createDate, ) from CfrData c where c.member.id = :memberId order by c.createDate desc")
    Page<BoardDto> findByBoardList(String boardTable, Pageable pageable);
}
