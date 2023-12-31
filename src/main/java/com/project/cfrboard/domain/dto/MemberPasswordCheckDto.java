package com.project.cfrboard.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberPasswordCheckDto {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

}
