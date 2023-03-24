package com.patandmat.otmz.domain.look.api.model;

import com.patandmat.otmz.domain.look.entity.Style;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecommendedLookResponse {

    private Long id;

    private Long imageId;

    private String imagePath;

    private Long memberId;

    private String memberNickname;

    private Style style;

    private Double similarity;

    @Builder
    public RecommendedLookResponse(Long id, Long imageId, String imagePath, Long memberId, String memberNickname, Style style, Double similarity) {
        this.id = id;
        this.imageId = imageId;
        this.imagePath = imagePath;
        this.memberId = memberId;
        this.memberNickname = memberNickname;
        this.style = style;
        this.similarity = similarity;
    }
}
