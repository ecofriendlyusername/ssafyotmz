package com.patandmat.otmz.domain.item.repository;

import com.patandmat.otmz.domain.item.entity.CrawledItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawledItemRepository extends JpaRepository<CrawledItem, Long> {
}
