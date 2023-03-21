package com.patandmat.otmz.domain.look.repository;

import com.patandmat.otmz.domain.look.api.model.LookCount;
import com.patandmat.otmz.domain.look.entity.Look;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LookRepository extends JpaRepository<Look, Long> {

    int countByMemberId(long memberId);

    @Query("SELECT new com.patandmat.otmz.domain.look.api.model.LookCount(l.style, COUNT(*)) FROM Look l WHERE l.member.id = :memberId GROUP BY l.style ORDER BY COUNT(*) DESC")
    List<LookCount> findByMemberIdOrderByStyleDesc(@Param("memberId") long memberId);

}