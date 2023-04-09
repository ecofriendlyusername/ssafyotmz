package com.patandmat.otmz.domain.item.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CrawledItem implements Serializable {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String url;

    @Column
    private String imageUrl;

    @Column
    private String price;

    @Column
    private String seller;

    @JsonIgnore
    @Column(length = 2000)
    private String styleVector;

    @Builder
    public CrawledItem(Long id, String name, String url, String imageUrl, String price, String seller, String styleVector) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.imageUrl = imageUrl;
        this.price = price;
        this.seller = seller;
        this.styleVector = styleVector;
    }
}