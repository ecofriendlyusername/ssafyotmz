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
    @Column(name = "account_image_id")
    private Long id;

    @Column(nullable = false)
    private String originName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String s3Key;

    @Column(nullable = false)
    private String path;

    @Builder
    public ImageFile(String originName, String name, String s3Key, String path) {
        this.originName = originName;
        this.name = name;
        this.s3Key = s3Key;
        this.path = path;
    }}
