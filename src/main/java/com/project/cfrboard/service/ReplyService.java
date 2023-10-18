package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.ReplyDto;
import com.project.cfrboard.domain.dto.ReplySaveDto;
import com.project.cfrboard.domain.entity.Reply;
import com.project.cfrboard.domain.repository.ReplyRepository;
import com.project.cfrboard.exception.NotMasterOfDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

        replyRepository.replySave(replySaveDto.getComment(), replySaveDto.getDepth(), replySaveDto.getBoardId(), memberId, replySaveDto.getReplyId(),
                LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public List<ReplyDto> sortReply(Long boardId) {
        List<ReplyDto> replyDtoList = replyRepository.findByBoard_IdOrderByReply_IdDescIdAsc(boardId);

        Stack<ReplyDto> stack = new Stack<>();
        List<ReplyDto> replyList = new ArrayList<>();

        for (int i = replyDtoList.size() - 1; i >= 0; i--) {
            if (replyDtoList.get(i).getParentReplyId() != null) {
                break;
            }
            stack.push(replyDtoList.get(i));
            replyDtoList.remove(i);
        }

        while (stack.size() > 0) {
            ReplyDto pop = stack.pop();
            replyList.add(pop);

            for (int i = replyDtoList.size() - 1; i >= 0; i--) {
                if (pop.getReplyId() < replyDtoList.get(i).getParentReplyId()) {
                    break;
                }
                if (pop.getReplyId() == replyDtoList.get(i).getParentReplyId()) {
                    stack.push(replyDtoList.get(i));
                    replyDtoList.remove(i);
                }
            }
        }

        return replyList;
    }

    public Boolean deletableCheck(Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("Reply ID %d로 찾을 수 없습니다.", replyId));
        });
        return reply.getChildCommentCount() == 0;
    }

    public void delete(Long replyId, Long memberId) throws NotMasterOfDataException {
        if (replyRepository.findByIdAndMember_Id(replyId, memberId).isEmpty()) {
            throw new NotMasterOfDataException("댓글 주인이 아닙니다.");
        }
        replyRepository.deleteById(replyId);
    }

    public void update(String comment, Long replyId, Long memberId) throws NotMasterOfDataException {
        if (replyRepository.findByIdAndMember_Id(replyId, memberId).isEmpty()) {
            throw new NotMasterOfDataException("댓글 주인이 아닙니다.");
        }
        replyRepository.updateComment(replyId, comment, LocalDateTime.now());
    }

    public void blind(Long replyId) {
        replyRepository.replyBlind(replyId);
    }

    @Transactional(readOnly = true)
    public Boolean isBlinded(Long replyId) {
        return getReply(replyId).getIsBlinded();
    }

    @Transactional(readOnly = true)
    private Reply getReply(Long replyId) {
        return replyRepository.findById(replyId).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("Reply ID %d로 찾을 수 없습니다.", replyId));
        });
    }


}
