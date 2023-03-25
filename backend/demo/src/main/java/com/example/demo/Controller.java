package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.AttributeNotFoundException;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
public class Controller {

    @PostMapping("/item")
    public ResponseEntity<?> saveItem(@RequestPart("imagefile") MultipartFile file, @RequestPart ItemRequestDto item, @RequestParam String category) {
        // Long member_id = 1L;
        System.out.println(category);
        return ResponseEntity.ok().build();
    }
}
