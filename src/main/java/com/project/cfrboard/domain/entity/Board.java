package com.project.cfrboard.domain.entity;

import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(schema = "cfrboard")
public class Board extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false)
    private String title;

    @Column(nullable = false, unique = false)
    @Lob
    private String content;

    @Column(nullable = false, unique = false)
    @Enumerated(EnumType.STRING)
    private BoardTable boardTable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cfrdata_id")
    private CfrData cfrData;

    @Builder
    public Board(String title, String content, BoardTable boardTable, CfrData cfrData) {
        this.title = title;
        this.content = content;
        this.boardTable = boardTable;
        this.cfrData = cfrData;
    }
}
