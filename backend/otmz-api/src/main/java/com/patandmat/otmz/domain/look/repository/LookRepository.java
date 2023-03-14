package com.patandmat.otmz.domain.look.repository;

import com.patandmat.otmz.domain.look.entity.Look;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LookRepository extends JpaRepository<Look, Long> {
}
