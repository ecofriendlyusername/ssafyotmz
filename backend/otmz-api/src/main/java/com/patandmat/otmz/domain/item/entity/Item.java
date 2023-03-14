package com.patandmat.otmz.domain.item.entity;

import com.patandmat.otmz.domain.common.BaseEntity;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 60)
    private String comment;

    @OneToOne
    private ImageFile image;
}
