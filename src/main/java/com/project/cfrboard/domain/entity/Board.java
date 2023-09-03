package com.project.cfrboard.domain.entity;

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

    @Builder
    public Board(String title, String content, BoardTable boardTable) {
        this.title = title;
        this.content = content;
        this.boardTable = boardTable;
    }
}
