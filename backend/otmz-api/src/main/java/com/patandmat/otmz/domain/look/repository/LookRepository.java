package com.patandmat.otmz.domain.look.repository;

import com.patandmat.otmz.domain.look.api.model.StyleByCountResponse;
import com.patandmat.otmz.domain.look.entity.Look;
import com.patandmat.otmz.domain.look.entity.Style;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LookRepository extends JpaRepository<Look, Long> {

    int countByMemberId(long memberId);

    @Query("SELECT new com.patandmat.otmz.domain.look.api.model.StyleByCountResponse(l.style, COUNT(*)) FROM Look l WHERE l.member.id = :memberId GROUP BY l.style ORDER BY COUNT(*) DESC")
    List<StyleByCountResponse> findByMemberIdOrderByStyleDesc(@Param("memberId") long memberId);

    List<Look> findAllByMemberIdNot(Long memberId);

    Page<Look> findAllByStyleOrderByCreatedAtDesc(Style style, Pageable pageable);
}