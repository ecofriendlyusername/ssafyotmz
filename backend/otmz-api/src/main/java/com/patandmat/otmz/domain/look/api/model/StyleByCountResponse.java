package com.patandmat.otmz.domain.look.api.model;

import com.patandmat.otmz.domain.look.entity.Style;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StyleByCountResponse implements Comparable<StyleByCountResponse> {
    private Style style;
    private long count;

    @Override
    public int compareTo(StyleByCountResponse o) {
        return (int) (o.count - this.count);
    }
}