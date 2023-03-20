package com.patandmat.otmz.domain.imageFile.application;

import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import org.springframework.web.multipart.MultipartFile;

public interface ImageFileService {
    ImageFile findOneImage(Long id);

    ImageFile save(MultipartFile imageFile);

    byte[] loadData(String path) throws Exception;
}
