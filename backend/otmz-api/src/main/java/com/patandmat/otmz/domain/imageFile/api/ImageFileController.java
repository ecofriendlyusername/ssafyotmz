package com.patandmat.otmz.domain.imageFile.api;

import com.patandmat.otmz.domain.imageFile.application.ImageFileService;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageFileController {

    private final ImageFileService imageFileService;

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) throws Exception {
        ImageFile imageFile = imageFileService.findOneImage(id);

        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", imageFile.getMimeType());
//        headers.add("Content-Length", String.valueOf(imageFile.getLength()));

        byte[] imageData = imageFileService.loadData(imageFile.getPath());

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    @PostMapping("/images")
    public ResponseEntity<Void> handleFileUpload(@RequestParam("image") MultipartFile file) {
        imageFileService.save(file);

        return ResponseEntity.ok().build();
    }
}
