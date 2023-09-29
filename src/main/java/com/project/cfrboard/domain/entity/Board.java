package com.project.cfrboard.domain.entity;

import com.project.cfrboard.domain.entity.enumeration.BoardTable;
import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

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
    private Integer todayViewCount;

    @Column(nullable = false, unique = false)
    private Integer totalViewCount;

    @Formula("(select count(*) from likes l where l.board_id = id)")
    private Integer likesCount;

    @Formula("(select count(*) from reply r where r.board_id = id)")
    private Integer commentCount;

    @Column(nullable = false, unique = false)
    @Enumerated(EnumType.STRING)
    private BoardTable boardTable;

    @Column(nullable = false, unique = false)
    private Boolean isBlinded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cfrdata_id")
    private CfrData cfrData;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList;

    @Builder
    public Board(String title, String content, BoardTable boardTable, Member member, CfrData cfrData) {
        this.title = title;
        this.content = content;
        this.boardTable = boardTable;
        this.member = member;
        this.cfrData = cfrData;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @PrePersist
    public void prePersist() {
        this.todayViewCount = 0;
        this.totalViewCount = 0;
        this.isBlinded = false;
    }
}
