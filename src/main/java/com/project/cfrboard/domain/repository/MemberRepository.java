package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    void deleteByUsername(String username);
}
