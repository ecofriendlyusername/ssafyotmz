package com.patandmat.otmz.domain.look.application;

import com.patandmat.otmz.domain.look.api.model.RecommendedLook;
import com.patandmat.otmz.domain.look.api.model.RecommendedLookResponse;
import com.patandmat.otmz.domain.look.entity.Look;
import com.patandmat.otmz.domain.look.repository.LookRepository;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.global.utils.SimilarityMeasurer;
import com.patandmat.otmz.global.utils.VectorParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LookRecommendServiceImpl implements LookRecommendService {

    private final LookRepository lookRepository;

    @Override
    public List<RecommendedLookResponse> getRecommendedLooks(Member member, int size, boolean reversed) {
        String stats = member.getStyleStat();
        Map<String, Double> memberStyleStats = VectorParser.parseToMap(stats);

        List<Look> looks = lookRepository.findAllByMemberIdNot(member.getId());

        List<RecommendedLook> recommendedLooks = looks.parallelStream()
                .map((look -> new Object[]{look, VectorParser.parseToMap(look.getStyleVector(), VectorParser.STYLE_KEY, VectorParser.STYLE_VALUE)}))
                .map(data -> new RecommendedLook((Look) data[0], SimilarityMeasurer.getCosineSimilarity(memberStyleStats, (Map<String, Double>) data[1])))
                .toList().stream().sorted().toList();

        int start = 0;
        int end = Math.min(size, recommendedLooks.size());

        if (reversed) {
            start = recommendedLooks.size() - end;
            end = recommendedLooks.size();
        }

        return recommendedLooks.subList(start, end)
                .stream()
                .map(recommendedLook -> RecommendedLookResponse
                        .builder()
                        .id(recommendedLook.getLook().getId())
                        .memberId(recommendedLook.getLook().getMember().getId()) // TODO N+1문제
                        .memberNickname(recommendedLook.getLook().getMember().getNickname())
                        .imageId(recommendedLook.getLook().getImage().getId())
                        .imagePath(recommendedLook.getLook().getImage().getPath())
                        .style(recommendedLook.getLook().getStyle())
                        .similarity(recommendedLook.getSimilarity())
                        .build())
                .toList();
    }
}
