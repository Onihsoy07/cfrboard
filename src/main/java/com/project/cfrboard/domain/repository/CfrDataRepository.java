package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.dto.CfrDataDto;
import com.project.cfrboard.domain.entity.CfrData;
import com.project.cfrboard.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CfrDataRepository extends JpaRepository<CfrData, Long> {

    @Query("select new com.project.cfrboard.domain.dto.CfrDataDto(c.value, c.confidence, c.createDate) from CfrData c where c.member.id = :memberId order by c.createDate desc")
    Page<CfrDataDto> findCfrDataDtoList(@Param("memberId") Long memberId, Pageable pageable);

    @Query("select new com.project.cfrboard.domain.dto.CfrDataDto(c.value, c.confidence, c.createDate) from CfrData c where c.member.id = :memberId order by c.createDate desc")
    List<CfrDataDto> findCfrDataDtoList(@Param("memberId") Long memberId);

}
