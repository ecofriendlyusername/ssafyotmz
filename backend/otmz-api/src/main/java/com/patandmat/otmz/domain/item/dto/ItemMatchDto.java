package com.patandmat.otmz.domain.item.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemMatchDto {
    private Long id;

    private String name;

    private String comment;
    private byte[] image;

    private String path;

    private Long imageId;

    @Builder
    public ItemMatchDto(Long id, String name, String comment, byte[] image, String path, Long imageId) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.image = image;
        this.path = path;
        this.imageId = imageId;
    }
}
