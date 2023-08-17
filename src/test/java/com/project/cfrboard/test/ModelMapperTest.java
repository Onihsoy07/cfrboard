package com.project.cfrboard.test;

import com.project.cfrboard.domain.dto.MemberLoginDto;
import com.project.cfrboard.domain.entity.Member;
import com.project.cfrboard.domain.entity.Role;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ModelMapperTest {

    @Autowired
    ModelMapper modelMapper;

    @Test
    void modelMapperTest() {
        //given
        Member member = Member.builder()
                .username("hello")
                .password("helloTest")
                .role(Role.USER)
                .build();

        MemberLoginDto loginDto = new MemberLoginDto();
        loginDto.setUsername("loginTest");
        loginDto.setPassword("loginTestPassword");

        //when
        MemberLoginDto result = modelMapper.map(member, MemberLoginDto.class);
        Member result1 = modelMapper.map(loginDto, Member.class);

        //then
        assertThat(member.getUsername()).isEqualTo(result.getUsername());
        assertThat(member.getPassword()).isEqualTo(result.getPassword());
        assertThat(result1.getUsername()).isEqualTo(loginDto.getUsername());
        assertThat(result1.getPassword()).isEqualTo(loginDto.getPassword());
    }
}
