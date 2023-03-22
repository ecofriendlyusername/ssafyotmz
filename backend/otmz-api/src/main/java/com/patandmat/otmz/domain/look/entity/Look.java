package com.patandmat.otmz.domain.look.entity;

import com.patandmat.otmz.domain.common.BaseEntity;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Look extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private ImageFile image;
    @ManyToOne
    private Member member;
    @Column(nullable = false, length = 600)
    private String styleVector;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Style style;

    @Builder
    public Look(Long id, ImageFile image, Member member, String styleVector) {
        this.id = id;
        this.image = image;
        this.member = member;
        this.styleVector = styleVector;
    }
}
