package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO LIKES(member_id, board_id) VALUES (:memberId, :boardId)", nativeQuery = true)
    void saveLike(@Param("memberId") Long memberId, @Param("boardId") Long boardId);

    Optional<Likes> findByMember_IdAndBoard_Id(Long memberId, Long boardId);
}
