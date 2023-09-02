package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.dto.CfrDataDto;
import com.project.cfrboard.domain.entity.CfrData;
import com.project.cfrboard.domain.entity.Member;
import com.project.cfrboard.domain.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class CfrDataRepositoryTest {

    @Autowired
    CfrDataRepository cfrDataRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void findCfrDataDtoList() {
        //given
        Member member = Member.builder()
                .username("test")
                .password("test")
                .role(Role.USER)
                .build();
        memberRepository.save(member);

        CfrData cfrData = CfrData.builder()
                .value("사람")
                .confidence(1F)
                .member(member)
                .build();
        cfrDataRepository.save(cfrData);

        Member findMember = memberRepository.findByUsername("test").get();

        Pageable pageable = PageRequest.of(0, 15, Sort.by("id"));

        //when
        Page<CfrDataDto> cfrDataDtoList = cfrDataRepository.findCfrDataDtoList(findMember.getId(), pageable);

        //then
        log.info("cfrDataDtoList = {}", cfrDataDtoList);
        assertThat(cfrDataDtoList.getContent().size()).isEqualTo(1);
        assertThat(cfrDataDtoList.getContent().get(0).getValue()).isEqualTo(cfrData.getValue());
        assertThat(cfrDataDtoList.getContent().get(0).getConfidence()).isEqualTo(cfrData.getConfidence());

    }
}