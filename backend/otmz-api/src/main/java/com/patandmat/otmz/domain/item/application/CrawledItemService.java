package com.patandmat.otmz.domain.item.application;

import com.patandmat.otmz.domain.item.dto.CrawledItemRequest;
import com.patandmat.otmz.domain.item.entity.CrawledItem;
import com.patandmat.otmz.domain.member.entity.Member;

import java.util.List;

public interface CrawledItemService {
    void addItems(List<CrawledItemRequest> itemsDto);

    List<CrawledItem> getRecommendItems(Member member);
}
