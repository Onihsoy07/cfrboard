package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.entity.Board;
import com.project.cfrboard.domain.entity.Inquiry;
import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    @Query("select distinct i from Inquiry i left join fetch i.member order by i.createDate desc")
    List<Inquiry> findPageList(Pageable pageable);

}
