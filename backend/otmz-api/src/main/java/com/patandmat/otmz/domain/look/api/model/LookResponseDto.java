package com.patandmat.otmz.domain.look.api.model;

import com.patandmat.otmz.domain.look.entity.Style;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LookResponseDto {
    private Long id;
    private Style style;
    private byte[] image;
    private String path;
    private Long imageId;
    private String name;
    private String mimeType;
    private String originName;

    @Builder
    public LookResponseDto(long id, Style style, Long imageId, String path, String name, String mimeType, String orignName) {
        this.id = id;
        this.style = style;
        this.imageId = imageId;
        this.path = path;
        this.name = name;
        this.mimeType = mimeType;
        this.originName = orignName;
    }
}
