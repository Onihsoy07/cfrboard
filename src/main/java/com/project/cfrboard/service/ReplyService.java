package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.ReplySaveDto;
import com.project.cfrboard.domain.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {

    private final ReplyRepository replyRepository;

    public void save(ReplySaveDto replySaveDto, Long memberId) {
        if (replySaveDto.getReplyId() == 0) {
            replySaveDto.setReplyId(null);
        }

        replyRepository.replySave(replySaveDto.getComment(), replySaveDto.getDepth(), replySaveDto.getBoardId(), memberId, replySaveDto.getReplyId());
    }
}
