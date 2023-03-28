package com.patandmat.otmz.domain.item.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemResponsePageDto {

    private Long id;
    private Long imageId;
    private String name;
    private String category;

    @Builder
    public ItemResponsePageDto(Long id, Long imageId, String name, String category) {
        this.id = id;
        this.imageId = imageId;
        this.name = name;
        this.category = category;
    }
}
