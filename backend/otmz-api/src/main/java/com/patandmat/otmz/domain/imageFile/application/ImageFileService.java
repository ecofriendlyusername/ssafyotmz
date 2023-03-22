package com.patandmat.otmz.domain.imageFile.application;

import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageFileService {
    ImageFile findOneImage(Long id);

    ImageFile save(MultipartFile imageFile);

    void delete(String path) throws IOException;

    byte[] loadData(String path) throws Exception;
}
