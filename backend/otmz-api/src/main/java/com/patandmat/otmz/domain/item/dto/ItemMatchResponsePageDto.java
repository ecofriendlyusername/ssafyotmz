package com.patandmat.otmz.domain.item.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemMatchResponsePageDto {
    private Long id;
    private String name;
    private String comment;
    private Long imageId;

    @Builder
    public ItemMatchResponsePageDto(Long id, String name, String comment, Long imageId) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.imageId = imageId;
    }
}
