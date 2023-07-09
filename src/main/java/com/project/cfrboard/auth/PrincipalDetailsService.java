package com.project.cfrboard.auth;

import com.project.cfrboard.domain.entity.Member;
import com.project.cfrboard.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member principal = memberRepository.findByUsername(username).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("UserName : %s 으로 Member를 찾을 수 없습니다.", username));
        });
        return new PrincipalDetails(principal);
    }
}
