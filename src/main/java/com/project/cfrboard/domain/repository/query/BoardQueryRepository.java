package com.project.cfrboard.domain.repository.query;

import com.project.cfrboard.domain.dto.BoardPageDto;
import com.project.cfrboard.domain.entity.Board;
import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.cfrboard.domain.entity.QBoard.*;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {

    private final JPAQueryFactory query;

    public Page<BoardPageDto> search(BoardTable boardTable, Pageable pageable, String target, String keyword) {
        List<Board> result = query.select(board)
                .from(board)
                .leftJoin(board.member).fetchJoin()
                .where(board.boardTable.eq(boardTable), searchCondition(target, keyword), board.isBlinded.eq(false))
                .orderBy(board.createDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(board.count())
                .from(board)
                .where(board.boardTable.eq(boardTable), searchCondition(target, keyword), board.isBlinded.eq(false))
                .fetchOne();

        List<BoardPageDto> searchBoardList = BoardPageDto.convertToDtoList(result);

        return new PageImpl<>(searchBoardList, pageable, count);
    }

    private BooleanBuilder searchCondition(String target, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();

        if ("all".equals(target) || "title_content".equals(target) || "title".equals(target)) {
            builder.or(board.title.contains(keyword));
        }
        if ("all".equals(target) || "title_content".equals(target) || "content".equals(target)) {
            builder.or(board.content.contains(keyword));
        }
        if ("all".equals(target) || "username".equals(target)) {
            builder.or(board.member.username.contains(keyword));
        }

        return builder;
    }
}
