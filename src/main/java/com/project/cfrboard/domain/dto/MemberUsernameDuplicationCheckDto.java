package com.project.cfrboard.domain.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class MemberUsernameDuplicationCheckDto {

    @Pattern(regexp = "^[a-z0-9]+$", message="아이디는 영어와 숫자의 조합을 사용해주세요.")
    @Size(min = 4, max = 10, message="아이디는 4~10자리로 해주세요.")
    private String username;
}
