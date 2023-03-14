package com.patandmat.otmz.domain.imageFile.repository;

import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
}
