package com.project.cfrboard.domain.dto;

import com.project.cfrboard.domain.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String username;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
