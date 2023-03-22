package com.patandmat.otmz.domain.item.dto;

import com.patandmat.otmz.domain.common.BaseEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemResponseDto extends BaseEntity {
    private Long id;

    private String name;
    private byte[] image;
    private String path;
    private Long imageId;
    private String category;

    @Builder
    public ItemResponseDto(Long id, String name, byte[] image, Long imageId, String category) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.imageId = imageId;
        this.category = category;
    }
}
