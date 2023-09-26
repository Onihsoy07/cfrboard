package com.project.cfrboard.domain.dto;

import com.project.cfrboard.domain.entity.Board;
import com.project.cfrboard.domain.entity.CfrData;
import com.project.cfrboard.domain.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CfrDataThumbDto {

    private MemberDto MemberDto;
    private String value;
    private Float confidence;

    public CfrDataThumbDto(CfrData cfrData) {
        this.MemberDto = new MemberDto(cfrData.getMember());
        this.value = cfrData.getValue();
        this.confidence = cfrData.getConfidence();
    }

    public static List<CfrDataThumbDto> convertToDtoList(Collection<CfrData> cfrDataList) {
        return cfrDataList.stream().map(CfrDataThumbDto::new).collect(Collectors.toList());
    }
}
