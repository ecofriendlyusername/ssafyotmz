package com.patandmat.otmz.domain.look.api.model;

import com.patandmat.otmz.domain.look.entity.Look;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LookSimilarity implements Comparable<LookSimilarity> {

    private final Look look;

    private final Double similarity;

    @Override
    public int compareTo(LookSimilarity other) {
        return (int) (other.similarity - this.similarity);
    }
}
