package com.patandmat.otmz.domain.member.api.model;

import com.patandmat.otmz.domain.item.api.model.ItemStyleByPercentResponse;
import com.patandmat.otmz.domain.look.api.model.StyleByCountResponse;
import com.patandmat.otmz.domain.look.api.model.StyleByPercentResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class MypageResponse {

    private String nickname;
    private int totalStyleCount;
    private int totalItemCount;
    private Map<String, Integer> itemStyle;
    private List<StyleByPercentResponse> styleByPercentResponseList;
    private List<StyleByPercentResponse> topStyleList;

    List<ItemStyleByPercentResponse> itemStyleByCountResponse;
    @Builder
    public MypageResponse(String nickname, int totalStyleCount, int totalItemCount, List<StyleByPercentResponse> styleByPercentResponseList, List<StyleByPercentResponse> topStyleList,Map<String, Integer> itemStyle,     List<ItemStyleByPercentResponse> itemStyleByCountResponse) {
        this.nickname = nickname;
        this.totalStyleCount = totalStyleCount;
        this.totalItemCount = totalItemCount;
        this.styleByPercentResponseList = styleByPercentResponseList;
        this.topStyleList = topStyleList;
        this.itemStyle = itemStyle;
        this.itemStyleByCountResponse = itemStyleByCountResponse;
    }
}
