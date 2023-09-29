package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.entity.Board;
import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {


    @Query("select distinct b from Board b left join fetch b.member where b.boardTable = :boardTable order by b.createDate desc")
    List<Board> findByBoardTable(@Param("boardTable") BoardTable boardTable, Pageable pageable);

    Long countByBoardTable(BoardTable boardTable);

    @Query("select b from Board b left join fetch b.member left join fetch b.cfrData where b.id = :id")
    Optional<Board> findByIdFetchMemberAndCfrdata(@Param("id") Long id);

    @Query("select b from Board b left join fetch b.member where b.id = :id")
    Optional<Board> findByIdFetchMember(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Board b set b.todayViewCount = b.todayViewCount+1, b.totalViewCount= b.totalViewCount+1 where b.id=:id")
    void addViewCount(@Param("id") Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Board b set b.todayViewCount = 0 where b.todayViewCount <> 0")
    void todayViewCountReset();

    List<Board> findTop10ByBoardTableOrderByCreateDateDesc(BoardTable boardTable);
    List<Board> findTop10ByTodayViewCountGreaterThanEqualOrderByTodayViewCountDescCreateDateDesc(int minView);


}
