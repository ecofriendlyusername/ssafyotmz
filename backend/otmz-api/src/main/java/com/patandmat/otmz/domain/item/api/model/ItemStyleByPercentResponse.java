package com.patandmat.otmz.domain.item.api.model;

import com.patandmat.otmz.domain.look.entity.Style;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemStyleByPercentResponse {
    private Style style;
    private double count;

}