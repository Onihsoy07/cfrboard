package com.project.cfrboard.domain.dto;

import com.project.cfrboard.domain.entity.Inquiry;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class InquiryDto {

    private Long id;
    private String title;
    private String content;
    private String target;
    private Boolean isCompleted;
    private MemberDto memberDto;
    private LocalDateTime createDate;

    public InquiryDto(Inquiry inquiry) {
        this.id = inquiry.getId();
        this.title = inquiry.getTitle();
        this.content = inquiry.getContent();
        this.target = inquiry.getTarget().toString().toLowerCase();
        this.isCompleted = inquiry.getIsCompleted();
        this.memberDto = new MemberDto(inquiry.getMember());
        this.createDate = inquiry.getCreateDate();
    }

}
