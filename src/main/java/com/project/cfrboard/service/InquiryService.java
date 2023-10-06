package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.InquiryFormDto;
import com.project.cfrboard.domain.entity.Inquiry;
import com.project.cfrboard.domain.entity.Member;
import com.project.cfrboard.domain.entity.enumeration.InquiryTarget;
import com.project.cfrboard.domain.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public void save(InquiryFormDto inquiryFormDto, Member member) {
        Inquiry inquiry = Inquiry.builder()
                .title(inquiryFormDto.getTitle())
                .content(inquiryFormDto.getContent())
                .isSecret(inquiryFormDto.getIsSecret())
                .target(InquiryTarget.valueOf(inquiryFormDto.getTarget().toUpperCase()))
                .member(member)
                .build();

        inquiryRepository.save(inquiry);
    }
}
