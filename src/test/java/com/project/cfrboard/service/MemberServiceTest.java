package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.MemberJoinDto;
import com.project.cfrboard.domain.entity.Member;
import com.project.cfrboard.domain.entity.Role;
import com.project.cfrboard.domain.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

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

    @Test
    void passwordCheckTrueTest() {
        //given
        String username = "member";

        MemberJoinDto memberJoinDto = new MemberJoinDto();
        memberJoinDto.setUsername(username);
        memberJoinDto.setPassword("memberps");
        memberJoinDto.setPasswordCheck("memberps");

        memberService.join(memberJoinDto);

        Member findMember = memberRepository.findByUsername(username).get();
        assertThat(findMember.getPasswordCheck()).isFalse();

        //when
        memberService.passwordCheckComplete(username);

        //then
        assertThat(findMember.getPasswordCheck()).isTrue();

    }
}