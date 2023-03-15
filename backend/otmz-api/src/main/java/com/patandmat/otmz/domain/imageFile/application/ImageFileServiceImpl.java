package com.patandmat.otmz.domain.imageFile.application;

import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.imageFile.repository.ImageFileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageFileServiceImpl implements ImageFileService {


    @Value("${file.image.path}")
    private String absolutePath;

    private final ImageFileRepository imageFileRepository;
    @Override
    public ImageFile findOneImage(Long id) {
        return imageFileRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    @Transactional
    public ImageFile save(MultipartFile multipartFile) {
        String type = multipartFile.getContentType().split("/")[1];
        String name = UUID.randomUUID() + "." + type;

        ImageFile imageFile = ImageFile.builder()
                .mineType(multipartFile.getContentType())
                .originName(multipartFile.getOriginalFilename())
                .name(name)
                .path(name)
                .length(multipartFile.getSize())
                .build();

        File file = new File(absolutePath + imageFile.getPath());

        try(OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imageFileRepository.save(imageFile);
    }

    @Override
    public byte[] loadData(String path) throws Exception {
        FileInputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            inputStream = new FileInputStream(absolutePath + path);
        }
        catch (FileNotFoundException e) {
            throw new Exception("해당 파일을 찾을 수 없습니다.");
        }

        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;

        try {
            while((readCount = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, readCount);
            }
            fileArray = outputStream.toByteArray();
            inputStream.close();
            outputStream.close();

        }
        catch (IOException e) {
            throw new Exception("파일을 변환하는데 문제가 발생했습니다.");
        }

        return fileArray;
    }
}
