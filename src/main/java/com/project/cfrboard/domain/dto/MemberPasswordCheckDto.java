package com.project.cfrboard.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class MemberPasswordCheckDto {

    @NotBlank(message = "비밀번호를 입력하시오")
    private String password;

    public MemberPasswordCheckDto(String password) {
        this.password = password;
    }
}
