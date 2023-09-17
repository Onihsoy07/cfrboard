package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.dto.ReplySaveDto;
import com.project.cfrboard.domain.entity.Reply;
import com.project.cfrboard.service.ReplyService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying
    @Query(value = "INSERT INTO REPLY(comment, depth, board_id, member_id, reply_id, create_date, update_date)"
            + "VALUES (:comment, :depth, :boardId, :memberId, :replyId, now(), now())", nativeQuery = true)
    void replySave(@Param("comment") String comment, @Param("depth") int depth, @Param("boardId") Long boardId,
                   @Param("memberId") Long memberId, @Param("replyId") Long replyId);
}
