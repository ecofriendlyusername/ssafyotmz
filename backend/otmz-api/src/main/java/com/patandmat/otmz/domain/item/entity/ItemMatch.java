package com.patandmat.otmz.domain.item.entity;

import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemMatch {
    @Id
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 15)
    private int category;
    @Column(nullable = false, length = 15)
    private int fabric;
    @Column(nullable = false, length = 15)
    private int print;
    @OneToOne
    private ImageFile image;
    @Column(nullable = false, length = 25)
    private String vector;

    @Builder
    public ItemMatch(Long id, String name, ImageFile image, String vector) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.vector = vector;
    }
}
