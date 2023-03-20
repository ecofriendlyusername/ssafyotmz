package com.patandmat.otmz.domain.item.dto;

import com.patandmat.otmz.domain.common.BaseEntity;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemDto extends BaseEntity {
    private Long id;

    private String name;
    private byte[] image;
    private String path;
    private Long imageId;
    private String category;
    private String vector;

    @Builder
    public ItemDto(Long id, String name, byte[] image, String path, Long imageId, String vector, String category) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.path = path;
        this.imageId = imageId;
        this.vector = vector;
        this.category = category;
    }
}
