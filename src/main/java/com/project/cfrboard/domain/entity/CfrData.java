package com.project.cfrboard.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(schema = "cfrboard")
public class CfrData extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false)
    private String value;

    @Column(nullable = false, unique = false)
    @OrderBy("createDate")
    private Float confidence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
