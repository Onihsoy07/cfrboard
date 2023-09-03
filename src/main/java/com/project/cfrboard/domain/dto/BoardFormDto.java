package com.project.cfrboard.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Lob;

@Data
@NoArgsConstructor
public class BoardFormDto {

    private String title;
    private String content;

}
