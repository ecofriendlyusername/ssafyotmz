package com.patandmat.otmz.domain.item.entity;

import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 20)
    private String comment;
    @OneToOne
    private ImageFile image;
    @ManyToOne
    private Member member;
    @Builder
    public ItemMatch(Long id, String name, ImageFile image, String comment, Long imageId, Member member) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.comment = comment;
        this.member = member;
    }
}
