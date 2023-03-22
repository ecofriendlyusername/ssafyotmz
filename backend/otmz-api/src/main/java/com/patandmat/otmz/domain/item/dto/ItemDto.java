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

    private String categoryVector;
    private String printVector;
    private String fabricVector;

    private String vector;

    @Builder
    public ItemDto(Long id, String name, byte[] image, String path, Long imageId, String vector, String category, String categoryVector, String printVector, String fabricVector) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.path = path;
        this.imageId = imageId;
        this.vector = vector;
        this.category = category;
        this.categoryVector = categoryVector;
        this.fabricVector = fabricVector;
        this.printVector = printVector;
    }
}
