package com.patandmat.otmz.domain.item.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemResponseDto {

    private Long id;
    private String name;
    private Long imageId;
    private String category;
    private String color;

    @Builder
    public ItemResponseDto(Long id, String name, Long imageId, String category, String color) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
        this.category = category;
        this.color = color;
    }
}
