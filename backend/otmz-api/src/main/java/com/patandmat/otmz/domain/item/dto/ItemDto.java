package com.patandmat.otmz.domain.item.dto;

import com.patandmat.otmz.domain.common.BaseEntity;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemDto extends BaseEntity {
    private Long id;

    private String name;

    private String comment;

    private String category;

    private String fabric;

    private String print;
    private byte[] image;

    @Builder
    public ItemDto(Long id, String name, String comment, byte[] image, String category, String fabric, String print) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.image = image;
        this.category = category;
        this.fabric = fabric;
        this.print = print;
    }
}
