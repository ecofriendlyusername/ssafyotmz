package com.patandmat.otmz.domain.member.repository;

import com.patandmat.otmz.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByAuthId(Long authId);
}
