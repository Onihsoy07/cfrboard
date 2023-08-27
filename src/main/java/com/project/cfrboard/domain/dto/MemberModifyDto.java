package com.project.cfrboard.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class MemberModifyDto {

    private String username;

    @NotBlank(message = "비밀번호를 입력하시오")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력하시오")
    private String passwordCheck;
}
