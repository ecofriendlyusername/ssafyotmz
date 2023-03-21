package com.patandmat.otmz.domain.item.repository;

import com.patandmat.otmz.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    int countByMemberId(long memberId);
}
