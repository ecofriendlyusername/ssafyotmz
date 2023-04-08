package com.patandmat.otmz.domain.look.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LookResponseDto {

    private Long id;
    private String ownerName;
    private String style;

    @Builder
    public LookResponseDto(Long id, String ownerName, String style) {
        this.id = id;
        this.ownerName = ownerName;
        this.style = style;
    }
}
