package com.patandmat.otmz.domain.item.repository;

import com.patandmat.otmz.domain.item.entity.ItemMatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemMatchRepository extends JpaRepository<ItemMatch, Long> {
    Page<ItemMatch> findAll(Pageable pageable);

    Page<ItemMatch> findAllByMemberId(Long member_id, Pageable pageable);
}
