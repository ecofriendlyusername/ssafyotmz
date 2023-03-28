package com.patandmat.otmz.domain.item.dto;

import com.patandmat.otmz.domain.common.BaseEntity;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemRequestDto {
    private String name;
    private String category;
    private String styleVector;
    private String color;
}
