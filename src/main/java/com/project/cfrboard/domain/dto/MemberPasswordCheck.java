package com.project.cfrboard.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class MemberPasswordCheck {

    @NotBlank(message = "비밀번호를 입력하시오")
    private String password;

}
