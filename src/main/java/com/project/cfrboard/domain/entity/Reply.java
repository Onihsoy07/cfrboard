package com.project.cfrboard.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Table(schema = "cfrboard")
public class Reply extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false)
    @Lob
    private String comment;

    @Column(nullable = false, unique = false)
    @ColumnDefault("0")
    private Integer depth;

    @Builder
    public Reply(String comment, Integer depth) {
        this.comment = comment;
        this.depth = depth;
    }
}
