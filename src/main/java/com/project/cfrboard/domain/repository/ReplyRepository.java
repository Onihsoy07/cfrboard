package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.dto.ReplyDto;
import com.project.cfrboard.domain.dto.ReplySaveDto;
import com.project.cfrboard.domain.entity.Reply;
import com.project.cfrboard.service.ReplyService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying
    @Query(value = "INSERT INTO reply(comment, depth, board_id, member_id, reply_id, create_date, update_date)"
            + "VALUES (:comment, :depth, :boardId, :memberId, :replyId, now(), now())", nativeQuery = true)
    void replySave(@Param("comment") String comment, @Param("depth") int depth, @Param("boardId") Long boardId,
                   @Param("memberId") Long memberId, @Param("replyId") Long replyId);

    @Query("select new com.project.cfrboard.domain.dto.ReplyDto(r.id, pr.id, m.username, r.comment, r.depth, r.createDate) "
            + "from Reply r left join r.member m left join r.reply pr where r.board.id = :boardId order by r.reply.id desc nulls last, r.id asc")
    List<ReplyDto> findByBoard_IdOrderByReply_IdDescIdAsc(@Param("boardId") Long boardId);

    Optional<Reply> findByIdAndMember_Id(Long replyId, Long memberId);

    @Modifying
    @Query("update Reply r set r.comment = :comment where r.id = :replyId")
    void updateComment(@Param("replyId") Long replyId, @Param("comment") String comment);
}
