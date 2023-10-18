package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.BoardPageDto;
import com.project.cfrboard.domain.dto.InquiryDto;
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

    @Transactional(readOnly = true)
    public InquiryDto getInquiryDetail(Long inquiryId) {
        return new InquiryDto(getInquiry(inquiryId));
    }

    public void complete(Long inquiryId) {
        inquiryRepository.inquiryComplete(inquiryId);
    }

    @Transactional(readOnly = true)
    public Page<InquiryPageDto> getMyInquiryList(Pageable pageable, String username) {
        List<Inquiry> searchInquiryList = inquiryRepository.findByMember_Username(username, pageable);
        return new PageImpl<>(InquiryPageDto.convertToDtoList(searchInquiryList), pageable, inquiryRepository.countByMember_Username(username));
    }

    @Transactional(readOnly = true)
    public Page<InquiryPageDto> getTargetInquiryList(Pageable pageable, String target) {
        List<Inquiry> searchInquiryList = inquiryRepository.findByTarget(InquiryTarget.valueOf(target.toUpperCase()), pageable);
        return new PageImpl<>(InquiryPageDto.convertToDtoList(searchInquiryList), pageable, inquiryRepository.countByTarget(InquiryTarget.valueOf(target.toUpperCase())));
    }

    @Transactional(readOnly = true)
    public Boolean isBoardMaster(Long memberId, Long inquiryId) {
        return inquiryRepository.findByIdAndMember_Id(inquiryId, memberId).isPresent();
    }

    @Transactional(readOnly = true)
    public Boolean deletableCheck(Long inquiryId) {
        return !getInquiry(inquiryId).getIsCompleted();
    }

    public void delete(Long inquiryId) {
        inquiryRepository.deleteById(inquiryId);
    }


    @Transactional(readOnly = true)
    private Inquiry getInquiry(Long inquiryId) {
        return inquiryRepository.findById(inquiryId).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("Inquiry ID %d로 찾을 수 없습니다.", inquiryId));
        });
    }
}
