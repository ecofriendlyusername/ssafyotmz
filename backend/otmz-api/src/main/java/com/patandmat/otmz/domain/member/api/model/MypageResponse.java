package com.patandmat.otmz.domain.member.api.model;

import com.patandmat.otmz.domain.look.api.model.StyleByCountResponse;
import com.patandmat.otmz.domain.look.api.model.StyleByPercentResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MypageResponse {

    private String nickname;
    private int totalStyleCount;
    private int totalItemCount;

    private List<StyleByPercentResponse> styleByPercentResponseList;
    private List<StyleByPercentResponse> topStyleList;

    @Builder
    public MypageResponse(String nickname, int totalStyleCount, int totalItemCount, List<StyleByPercentResponse> styleByPercentResponseList, List<StyleByPercentResponse> topStyleList) {
        this.nickname = nickname;
        this.totalStyleCount = totalStyleCount;
        this.totalItemCount = totalItemCount;
        this.styleByPercentResponseList = styleByPercentResponseList;
        this.topStyleList = topStyleList;
    }
}
