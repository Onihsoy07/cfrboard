package com.project.cfrboard.domain.repository;

import com.project.cfrboard.domain.entity.CfrData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfrDataRepository extends JpaRepository<CfrData, Long> {
}
