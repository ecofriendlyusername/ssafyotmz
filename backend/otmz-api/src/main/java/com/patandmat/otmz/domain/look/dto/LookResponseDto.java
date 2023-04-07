package com.patandmat.otmz.domain.look.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LookResponseDto {

    private Long id;
    private String uploader;
    private String style;

    @Builder
    public LookResponseDto(Long id, String uploader, String style) {
        this.id = id;
        this.uploader = uploader;
        this.style = style;
    }
}
