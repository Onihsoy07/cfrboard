package com.project.cfrboard.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CfrDataDto {

    private Long id;
    private String value;
    private Float confidence;
    private LocalDateTime createDate;

    public CfrDataDto(Long id, String value, Float confidence, LocalDateTime createDate) {
        this.id = id;
        this.value = value;
        this.confidence = confidence;
        this.createDate = createDate;
    }
}
