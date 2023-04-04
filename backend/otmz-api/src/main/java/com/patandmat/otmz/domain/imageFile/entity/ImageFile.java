package com.patandmat.otmz.domain.imageFile.entity;

import com.patandmat.otmz.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private String originName;

//    @Column(nullable = false)
//    private String name;
    @Column(nullable = false)
    private String path;

//    @Column(nullable = false)
//    private String mimeType;

//    @Column(nullable = false)
//    private Long length;

    @Builder
    public ImageFile(String originName, String name, String path, String mineType, Long length) {
//        this.originName = originName;
//        this.name = name;
        this.path = path;
//        this.mimeType = mineType;
//        this.length = length;
    }
}
