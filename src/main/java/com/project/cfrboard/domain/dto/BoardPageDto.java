package com.project.cfrboard.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardPageDto {

    private Long id;
    private String title;
    private LocalDateTime createDate;
    private Integer viewCount;
    private Integer likesCount;
    private MemberDto memberDto;


}
