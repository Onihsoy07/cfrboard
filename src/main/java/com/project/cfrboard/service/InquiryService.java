package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.InquiryFormDto;
import com.project.cfrboard.domain.dto.InquiryPageDto;
import com.project.cfrboard.domain.entity.Inquiry;
import com.project.cfrboard.domain.entity.Member;
import com.project.cfrboard.domain.entity.enumeration.InquiryTarget;
import com.project.cfrboard.domain.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public Page<InquiryPageDto> getInquiryList(Pageable pageable) {
        return new PageImpl<>(InquiryPageDto.convertToDtoList(inquiryRepository.findPageList(pageable)), pageable, inquiryRepository.count());
    }
}
