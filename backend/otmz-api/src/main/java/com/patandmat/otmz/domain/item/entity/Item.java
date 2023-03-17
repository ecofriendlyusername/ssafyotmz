package com.patandmat.otmz.domain.item.entity;

import com.patandmat.otmz.domain.common.BaseEntity;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
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
    @Column(nullable = false, length = 15)
    private int fabric;
    @Column(nullable = false, length = 15)
    private int print;
    @OneToOne
    private ImageFile image;
    @Column(nullable = false, length = 25)
    private String vector;



    @Builder
    public Item(Long id, String name, ImageFile image, int category, int fabric, int print, String vector) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
        this.fabric = fabric;
        this.print = print;
        this.vector = vector;
    }
}
