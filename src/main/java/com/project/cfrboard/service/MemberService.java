package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.MemberJoinDto;
import com.project.cfrboard.domain.entity.Member;
import com.project.cfrboard.domain.entity.Role;
import com.project.cfrboard.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(MemberJoinDto memberJoinDto) {
        Member member = Member.builder()
                .username(memberJoinDto.getUsername())
                .password(passwordEncoder.encode(memberJoinDto.getPassword()))
                .role(Role.USER)
                .build();

        memberRepository.save(member);
    }


}
