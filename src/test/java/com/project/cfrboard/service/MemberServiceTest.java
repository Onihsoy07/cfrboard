package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.MemberJoinDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void usernameDuplicateCheck() {
        //given
        MemberJoinDto memberJoinDto = new MemberJoinDto();
        memberJoinDto.setUsername("member");
        memberJoinDto.setPassword("memberps");
        memberJoinDto.setPasswordCheck("memberps");

        memberService.join(memberJoinDto);

        //when
        Boolean isDuplicate = memberService.usernameDuplicateCheck(memberJoinDto.getUsername());

        //then
        assertThat(isDuplicate).isTrue();

    }
}