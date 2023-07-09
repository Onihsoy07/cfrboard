package com.project.cfrboard.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Base {

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime createDate;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime updateDate;

}
