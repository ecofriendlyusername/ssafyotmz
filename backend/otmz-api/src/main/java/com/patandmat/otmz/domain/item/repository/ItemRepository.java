package com.patandmat.otmz.domain.item.repository;

import com.patandmat.otmz.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAllByCategory(int categoryNum, Pageable pageable);
    Page<Item> findAllByCategoryAndMemberId(int categoryNum, Long id, Pageable pageable);
}
