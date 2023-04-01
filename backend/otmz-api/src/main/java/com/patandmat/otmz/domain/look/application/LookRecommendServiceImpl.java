package com.patandmat.otmz.domain.look.application;

import com.patandmat.otmz.domain.look.api.model.LookSimilarity;
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
        String stats = member.getLookStyleStat();
        Map<String, Double> memberStyleStats = VectorParser.parseToMap(stats);

        List<Look> looks = lookRepository.findAllByMemberIdNot(member.getId());

        List<LookSimilarity> lookSimilarities = looks.parallelStream()
                .map((look -> new Object[]{look, VectorParser.parseToMap(look.getStyleVector(), VectorParser.STYLE_KEY, VectorParser.STYLE_VALUE)}))
                .map(data -> new LookSimilarity((Look) data[0], SimilarityMeasurer.getCosineSimilarity(memberStyleStats, (Map<String, Double>) data[1])))
                .toList().stream().sorted().toList();

        int start = 0;
        int end = Math.min(size, lookSimilarities.size());

        if (reversed) {
            start = lookSimilarities.size() - end;
            end = lookSimilarities.size();
        }

        return lookSimilarities.subList(start, end)
                .stream()
                .map(lookSimilarity -> RecommendedLookResponse
                        .builder()
                        .id(lookSimilarity.getLook().getId())
                        .memberId(lookSimilarity.getLook().getMember().getId()) // TODO N+1문제
                        .memberNickname(lookSimilarity.getLook().getMember().getNickname())
                        .imageId(lookSimilarity.getLook().getImage().getId())
                        .imagePath(lookSimilarity.getLook().getImage().getPath())
                        .style(lookSimilarity.getLook().getStyle())
                        .similarity(lookSimilarity.getSimilarity())
                        .build())
                .toList();
    }
}
