package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
