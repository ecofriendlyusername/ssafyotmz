package com.patandmat.otmz.domain.item.api;

import com.patandmat.otmz.domain.item.application.CrawledItemService;
import com.patandmat.otmz.domain.item.dto.CrawledItemRequest;
import com.patandmat.otmz.domain.item.entity.CrawledItem;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.global.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items/brandi")
@RequiredArgsConstructor
public class CrawledItemController {

    private final CrawledItemService crawledItemService;

    @PostMapping("")
    public ResponseEntity<Void> addItems(@RequestBody List<CrawledItemRequest> itemsDto) {
        crawledItemService.addItems(itemsDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<List<CrawledItem>> getRecommendedItem(Authentication authentication) { // 현재는 10개까지만 반환
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        List<CrawledItem> items = crawledItemService.getRecommendItems(member);

        return ResponseEntity.ok(items);
    }
}
