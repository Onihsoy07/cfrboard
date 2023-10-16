package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.entity.Board;
import com.project.cfrboard.domain.entity.Inquiry;
import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import com.project.cfrboard.domain.entity.enumeration.InquiryTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    @Query("select distinct i from Inquiry i left join fetch i.member order by i.createDate desc")
    List<Inquiry> findPageList(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Inquiry i set i.isCompleted = 1 where i.id = :id")
    void inquiryComplete(@Param("id") Long id);

    @Query("select distinct i from Inquiry i left join fetch i.member where i.member.username = :username order by i.createDate desc")
    List<Inquiry> findByMember_Username(@Param("username") String username);

    Long countByMember_Username(String username);

    @Query("select distinct i from Inquiry i left join fetch i.member where i.target = :target order by i.createDate desc")
    List<Inquiry> findByTarget(@Param("target") InquiryTarget target);

    Long countByTarget(InquiryTarget target);

}
