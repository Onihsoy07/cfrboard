package com.project.cfrboard.domain.entity;

import com.project.cfrboard.domain.entity.enumeration.InquiryTarget;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(schema = "cfrboard")
public class Inquiry extends Base {

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
    private Boolean isSecret;

    @Column(nullable = false, unique = false)
    @Enumerated(EnumType.STRING)
    private InquiryTarget target;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Inquiry(String title, String content, Boolean isSecret, InquiryTarget target, Member member) {
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
        this.target = target;
        this.member = member;
    }
}
