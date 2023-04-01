package com.patandmat.otmz.domain.look.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LookResponse {

    private Long id;

    private Long imageId;

    private Long memberId;

    private String ownerName;

    private String style;

    @Builder
    public LookResponse(Long id, Long imageId, Long memberId, String ownerName, String style) {
        this.id = id;
        this.imageId = imageId;
        this.memberId = memberId;
        this.ownerName = ownerName;
        this.style = style;
    }
}
