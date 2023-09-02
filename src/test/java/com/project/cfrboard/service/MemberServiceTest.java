package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.MemberJoinDto;
import com.project.cfrboard.domain.dto.MemberPasswordCheckDto;
import com.project.cfrboard.domain.dto.MemberUpdateDto;
import com.project.cfrboard.domain.entity.Member;
import com.project.cfrboard.domain.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        MemberJoinDto memberJoinDto = createMemberJoinDto("member", "memberps");

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

        MemberJoinDto memberJoinDto = createMemberJoinDto(username, "password");

        memberService.join(memberJoinDto);

        Member findMember = memberRepository.findByUsername(username).get();
        assertThat(findMember.getPasswordCheck()).isFalse();

        //when
        memberService.passwordCheckComplete(username);

        //then
        assertThat(findMember.getPasswordCheck()).isTrue();

    }

    @Test
    void passwordCheckTest() {
        //given
        String username = "member";
        String password = "memberps";

        MemberJoinDto memberJoinDto = createMemberJoinDto(username, password);

        memberService.join(memberJoinDto);

        Member findMember = memberRepository.findByUsername(username).get();

        MemberPasswordCheckDto passwordCheckDto = new MemberPasswordCheckDto(password);

        //when
        Boolean aBoolean = memberService.passwordCheck(passwordCheckDto, findMember.getPassword());

        //then
        assertThat(aBoolean).isTrue();

    }

    @Test
    void update() {
        //given
        String username = "member";
        String password = "memberps";
        String updatePassword = "updateMemberps";

        MemberJoinDto memberJoinDto = createMemberJoinDto(username, password);
        MemberUpdateDto updateDto = new MemberUpdateDto(updatePassword, updatePassword);
        MemberPasswordCheckDto passwordCheckDto = new MemberPasswordCheckDto(updatePassword);

        memberService.join(memberJoinDto);

        Member findMember = memberRepository.findByUsername(username).get();

        //when
        String result = memberService.update(updateDto, username);

        //then
        assertThat(result).isEqualTo("ok");
        assertThat(memberService.passwordCheck(passwordCheckDto, findMember.getPassword())).isTrue();

    }

    @Test
    void delete() {
        //given
        String username = "member";
        String password = "memberps";

        MemberJoinDto memberJoinDto = createMemberJoinDto(username, password);
        memberService.join(memberJoinDto);

        Optional<Member> findMember = memberRepository.findByUsername(username);
        assertThat(findMember.isPresent()).isTrue();

        //when
        memberService.delete(findMember.get().getId());

        //then
        findMember = memberRepository.findByUsername(username);
        assertThat(findMember.isPresent()).isFalse();
    }

    private MemberJoinDto createMemberJoinDto(String username, String password) {
        MemberJoinDto memberJoinDto = new MemberJoinDto();
        memberJoinDto.setUsername(username);
        memberJoinDto.setPassword(password);
        memberJoinDto.setPasswordCheck(password);

        return memberJoinDto;
    }

}