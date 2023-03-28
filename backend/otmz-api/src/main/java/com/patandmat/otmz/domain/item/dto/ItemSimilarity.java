package com.patandmat.otmz.domain.item.dto;

import com.patandmat.otmz.domain.item.entity.CrawledItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ItemSimilarity implements Comparable<ItemSimilarity> {

    private final CrawledItem item;

    private final Double similarity;

    @Override
    public int compareTo(ItemSimilarity other) {
        return (int) (other.similarity - this.similarity);
    }
}
