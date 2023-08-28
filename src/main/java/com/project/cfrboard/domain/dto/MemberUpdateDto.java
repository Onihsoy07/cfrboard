package com.project.cfrboard.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberUpdateDto {

    private String password;
    private String passwordCheck;
}
