package com.patandmat.otmz.domain.item.dto;

import com.patandmat.otmz.domain.common.BaseEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemResponsePageDto {
    private Long id;
    private Long imageId;
    @Builder
    public ItemResponsePageDto(Long id, Long imageId) {
        this.id = id;
        this.imageId = imageId;
    }
}
