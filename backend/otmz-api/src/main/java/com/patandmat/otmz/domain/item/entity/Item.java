package com.patandmat.otmz.domain.item.entity;

import com.patandmat.otmz.domain.common.BaseEntity;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 15)
    private int category;

    @OneToOne
    private ImageFile image;
    @Column(length = 2000)
    private String styleVector;

    @Column(nullable = false, length = 600)
    private String color;

    @ManyToOne
    private Member member;

    @Builder
    public Item(Long id, String name, ImageFile image, int category, String styleVector, String color, Member member) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
        this.styleVector = styleVector;
        this.color = color;
        this.member = member;
    }
}
