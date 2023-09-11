package com.project.cfrboard.domain.dto;

import com.project.cfrboard.domain.entity.CfrData;
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

    public CfrDataDto(CfrData cfrData) {
        this.id = cfrData.getId();
        this.value = cfrData.getValue();
        this.confidence = cfrData.getConfidence();
        this.createDate = cfrData.getCreateDate();
    }

    public CfrDataDto(Long id, String value, Float confidence, LocalDateTime createDate) {
        this.id = id;
        this.value = value;
        this.confidence = confidence;
        this.createDate = createDate;
    }
}
