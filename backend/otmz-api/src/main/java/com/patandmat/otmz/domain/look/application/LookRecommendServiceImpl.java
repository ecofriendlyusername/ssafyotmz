package com.patandmat.otmz.domain.look.application;

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

        List<Look> looks = lookRepository.findAll();

        List<RecommendedLookResponse> result = looks.parallelStream()
                .map((look -> new Object[]{look, VectorParser.parseToMap(look.getStyleVector(), VectorParser.STYLE_KEY, VectorParser.STYLE_VALUE)}))
                .map(data -> new RecommendedLookResponse((Look) data[0], SimilarityMeasurer.getCosineSimilarity(memberStyleStats, (Map<String, Double>) data[1])))
                .toList().stream().sorted().toList();

        size = Math.min(size, result.size());

        if (reversed) {
            return result.subList(result.size() - size, result.size());
        }

        return result.subList(0, size);
    }
}
