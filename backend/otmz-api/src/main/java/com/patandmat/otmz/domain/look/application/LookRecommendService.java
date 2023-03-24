package com.patandmat.otmz.domain.look.application;

import com.patandmat.otmz.domain.look.api.model.RecommendedLookResponse;
import com.patandmat.otmz.domain.member.entity.Member;

import java.util.List;

public interface LookRecommendService {
    List<RecommendedLookResponse> getRecommendedLooks(Member member, int size, boolean reversed);
}
