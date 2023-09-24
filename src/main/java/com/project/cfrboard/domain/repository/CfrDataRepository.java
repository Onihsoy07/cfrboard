package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.dto.CfrDataDto;
import com.project.cfrboard.domain.entity.CfrData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CfrDataRepository extends JpaRepository<CfrData, Long> {

    @Query("select new com.project.cfrboard.domain.dto.CfrDataDto(c.id, c.value, c.confidence, c.createDate) from CfrData c where c.member.id = :memberId order by c.createDate desc")
    Page<CfrDataDto> findCfrDataDtoList(@Param("memberId") Long memberId, Pageable pageable);

    @Query("select new com.project.cfrboard.domain.dto.CfrDataDto(c.id, c.value, c.confidence, c.createDate) from CfrData c where c.member.id = :memberId order by c.createDate desc")
    List<CfrDataDto> findCfrDataDtoList(@Param("memberId") Long memberId);

    @Query("select count(*) from CfrData c where c.createDate > :today")
    int countByAfterMidnight(@Param("today") LocalDateTime today);

    @Query("select count(*) from CfrData c where c.member.id = :memberId and c.createDate > :today")
    int countByMember_IdAndAfterMidnight(@Param("memberId") Long memberId, @Param("today") LocalDateTime today);

}

