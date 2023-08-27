package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.MemberJoinDto;
import com.project.cfrboard.domain.dto.MemberPasswordCheckDto;
import com.project.cfrboard.domain.entity.Member;
import com.project.cfrboard.domain.entity.Role;
import com.project.cfrboard.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(MemberJoinDto memberJoinDto) {
        Member member = Member.builder()
                .username(memberJoinDto.getUsername())
                .password(passwordEncoder.encode(memberJoinDto.getPassword()))
                .role(Role.USER)
                .build();

        memberRepository.save(member);
    }


    /**
     * 아이디 중복 체크
     * 중복 -> return true;
     * 중복 없음 -> return false;
     */
    @Transactional(readOnly = true)
    public Boolean usernameDuplicateCheck(String username) {
        return memberRepository.findByUsername(username).isPresent();
    }

    public void passwordCheckComplete(String username) {
        Member member = findByUsername(username);
        member.setPasswordCheckTrue();
    }

    public void passwordCheckReset(String username) {
        Member member = findByUsername(username);
        member.setPasswordCheckFalse();
    }

    public Boolean passwordCheck(MemberPasswordCheckDto passwordCheckDto,
                                 String password) {
        if (passwordEncoder.matches(passwordCheckDto.getPassword(), password)) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public Boolean passwordCheckIsTrue(String username) {
        Member member = findByUsername(username);
        if (member.getPasswordCheck()) {
            return true;
        } else {
            return false;
        }
    }

    private Member findByUsername(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("username : %s 를 찾을 수 없습니다.", username));
        });
    }


}
