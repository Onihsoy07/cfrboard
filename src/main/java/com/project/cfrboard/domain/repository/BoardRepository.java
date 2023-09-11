package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.dto.BoardDto;
import com.project.cfrboard.domain.entity.Board;
import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {


    @Query("select distinct b from Board b left join fetch b.member where b.boardTable = :boardTable order by b.createDate desc")
    List<Board> findByBoardTable(@Param("boardTable") BoardTable boardTable, Pageable pageable);

    Long countByBoardTable(BoardTable boardTable);

    @Query("select b from Board b left join fetch b.member left join fetch b.cfrData where b.id = :id")
    Optional<Board> findByIdFetch(@Param("id") Long id);


}
