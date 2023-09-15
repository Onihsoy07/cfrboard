package com.project.cfrboard.service;

import com.project.cfrboard.domain.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LikesService {

    private final LikesRepository likesRepository;

    public void save(Long memberId, Long boardId) {
        likesRepository.saveLike(memberId, boardId);
    }

    /**
     *
     * 이 게시글에 좋아요 있으면 true 리턴
     * 이 게시글에 좋아요 없으면 false 리턴
     */
    @Transactional(readOnly = true)
    public Boolean isBoardLikes(Long memberId, Long boardId) {
        return likesRepository.findByMember_IdAndBoard_Id(memberId, boardId).isPresent();
    }
}
