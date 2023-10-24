package com.project.cfrboard.domain.dto;

import com.project.cfrboard.domain.constant.MyConstant;
import com.project.cfrboard.domain.entity.Board;
import com.project.cfrboard.domain.entity.Inquiry;
import com.project.cfrboard.domain.entity.Member;
import com.project.cfrboard.domain.entity.enumeration.InquiryTarget;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.cfrboard.domain.constant.MyConstant.*;

@Data
@NoArgsConstructor
public class InquiryPageDto {

    private Long id;
    private String title;
    private String target;
    private Boolean isSecret;
    private Boolean isCompleted;
    private MemberDto memberDto;
    private LocalDateTime createDate;

    public InquiryPageDto(Inquiry inquiry) {
        this.id = inquiry.getId();
        this.title = inquiry.getTitle();
        this.target = INQUIRY_TARGET_TO_KOREAN.get(inquiry.getTarget().toString().toLowerCase());
        this.isSecret = inquiry.getIsSecret();
        this.isCompleted = inquiry.getIsCompleted();
        this.memberDto = new MemberDto(inquiry.getMember());
        this.createDate = inquiry.getCreateDate();
    }

    public static List<InquiryPageDto> convertToDtoList(Collection<Inquiry> inquiryList) {
        return inquiryList.stream().map(InquiryPageDto::new).collect(Collectors.toList());
    }
}
