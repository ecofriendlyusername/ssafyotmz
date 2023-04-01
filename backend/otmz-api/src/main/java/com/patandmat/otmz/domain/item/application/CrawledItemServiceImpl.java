package com.patandmat.otmz.domain.item.application;

import com.patandmat.otmz.domain.item.dto.CrawledItemRequest;
import com.patandmat.otmz.domain.item.dto.ItemSimilarity;
import com.patandmat.otmz.domain.item.entity.CrawledItem;
import com.patandmat.otmz.domain.item.repository.CrawledItemRepository;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.global.utils.SimilarityMeasurer;
import com.patandmat.otmz.global.utils.VectorParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrawledItemServiceImpl implements CrawledItemService {

    private final CrawledItemRepository crawledItemRepository;

    @Override
    public void addItems(List<CrawledItemRequest> itemsDto) {
        itemsDto.stream()
                .map(CrawledItemRequest::toEntity)
                .forEach(crawledItemRepository::save);
    }

    @Override
    public List<CrawledItem> getRecommendItems(Member member) {
        String stats = member.getItemStyleStat();
        Map<String, Double> memberStyleStats = VectorParser.parseToMap(stats);

        List<CrawledItem> items = crawledItemRepository.findAll();

        List<ItemSimilarity> itemSimilarities = items.parallelStream()
                .map((item -> new Object[]{item, VectorParser.parseToMap(item.getStyleVector(), VectorParser.STYLE_KEY, VectorParser.STYLE_VALUE)}))
                .map(data -> new ItemSimilarity((CrawledItem) data[0], SimilarityMeasurer.getCosineSimilarity(memberStyleStats, (Map<String, Double>) data[1])))
                .toList().stream().sorted().toList();

        return itemSimilarities
                .subList(0, 10)
                .stream()
                .map(ItemSimilarity::getItem)
                .collect(Collectors.toList());
    }
}
