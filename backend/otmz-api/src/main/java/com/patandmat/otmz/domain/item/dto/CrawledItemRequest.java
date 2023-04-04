package com.patandmat.otmz.domain.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patandmat.otmz.domain.item.entity.CrawledItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CrawledItemRequest {

    private Long id;

    private String name;

    private String url;

    private String imageUrl;

    private String price;

    private String seller;

    @JsonProperty(value = "style_vector")
    private String styleVector;

    public CrawledItem toEntity() {
        return CrawledItem.builder()
                .id(this.id)
                .name(this.name)
                .url(this.url)
                .imageUrl(this.imageUrl)
                .price(this.price)
                .seller(this.seller)
                .styleVector(this.styleVector)
                .build();
    }
}
