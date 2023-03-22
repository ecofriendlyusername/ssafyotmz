package com.patandmat.otmz.domain.item.dto;

import com.patandmat.otmz.domain.common.BaseEntity;
import lombok.*;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemRequestDto extends BaseEntity {
    private String name;
    private String category;
    private String categoryVector;
    private String printVector;
    private String fabricVector;
}
