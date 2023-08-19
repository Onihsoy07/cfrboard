package com.project.cfrboard.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CfrDataDto {

    private String value;
    private Float confidence;
    private LocalDateTime createDate;

    public CfrDataDto(String value, Float confidence, LocalDateTime createDate) {
        this.value = value;
        this.confidence = confidence;
        this.createDate = createDate;
    }
}
