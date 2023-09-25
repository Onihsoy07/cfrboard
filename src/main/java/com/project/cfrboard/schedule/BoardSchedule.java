package com.project.cfrboard.schedule;

import com.project.cfrboard.domain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardSchedule {

    private final BoardRepository boardRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    private void todayViewCountReset() {
        log.info("Today View Count 초기화 진행");
        boardRepository.todayViewCountReset();
    }

}
