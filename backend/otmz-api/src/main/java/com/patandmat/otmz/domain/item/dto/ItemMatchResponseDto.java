package com.patandmat.otmz.domain.item.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemMatchResponseDto {
    private Long id;
    private String name;
    private String comment;
    private byte[] image;
    private Long imageId;

    @Builder
    public ItemMatchResponseDto(Long id, String name, String comment, byte[] image, Long imageId) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.image = image;
        this.imageId = imageId;
    }
}
