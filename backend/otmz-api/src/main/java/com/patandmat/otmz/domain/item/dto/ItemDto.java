package com.patandmat.otmz.domain.item.dto;

import com.patandmat.otmz.domain.common.BaseEntity;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemDto extends BaseEntity {
    private Long id;

    private String name;

    private String comment;
    private byte[] image;

    private String path;

    private Long imageId;

    private String vector;

    @Builder
    public ItemDto(Long id, String name, String comment, byte[] image, String path, Long imageId, String vector) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.image = image;
        this.path = path;
        this.imageId = imageId;
        this.vector = vector;
    }
}
