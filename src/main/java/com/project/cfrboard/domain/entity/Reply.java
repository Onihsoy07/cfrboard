package com.project.cfrboard.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private Integer depth;

    @Column(nullable = false, unique = false)
    private Boolean isBlinded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;

    @OneToMany(mappedBy = "reply", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList;

    @Builder
    public Reply(String comment, Integer depth) {
        this.comment = comment;
        this.depth = depth;
    }

    @PrePersist
    public void prePersist() {
        this.depth = 0;
        this.isBlinded = false;
    }
}
