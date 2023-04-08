package com.patandmat.otmz.domain.item.api.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemStyleByCountResponse implements Comparable<ItemStyleByCountResponse> {
    private String style;
    private long count;

    @Override
    public int compareTo(ItemStyleByCountResponse o) {
        return (int) (o.count - this.count);
    }
}