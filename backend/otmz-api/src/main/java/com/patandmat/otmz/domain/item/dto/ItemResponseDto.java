package com.patandmat.otmz.domain.item.dto;

import com.patandmat.otmz.domain.common.BaseEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemResponseDto {
    private Long id;

    private String name;
    private byte[] image;
    private String path;
    private Long imageId;
    private String category;

    private String color;

    @Builder
    public ItemResponseDto(Long id, String name, byte[] image, Long imageId, String category, String color) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.imageId = imageId;
        this.category = category;
        this.color = color;
    }
}
