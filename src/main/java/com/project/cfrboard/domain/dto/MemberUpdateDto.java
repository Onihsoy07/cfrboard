package com.project.cfrboard.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDto {

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{0,999}$", message="비밀번호는 영어와 숫자의 조합을 사용해주세요.")
    @Size(min = 4, max = 20, message="비밀번호는 4~20자리로 해주세요.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String passwordCheck;
}
