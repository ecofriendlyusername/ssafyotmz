package com.patandmat.otmz.domain.item.controller;

import com.patandmat.otmz.domain.item.dto.ItemDto;
import com.patandmat.otmz.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.AttributeNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @PostMapping("/item")
    public ResponseEntity<?> saveItem(@RequestPart("imagefile") MultipartFile file, @RequestPart ItemDto item) throws IOException {
        // take name, comment
        try {
            itemService.saveItem(file,item);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<String>("User Does Not Exist", HttpStatus.BAD_REQUEST);
        } catch (AttributeNotFoundException e) {
            return new ResponseEntity<String>("Check Attributes of The Item", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/item")
    public ResponseEntity<?> getItem(@RequestParam Long id) throws IOException {
        // take name, comment
        try {
            ItemDto itemDto = itemService.getItem(id);
            return new ResponseEntity<ItemDto>(itemDto,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<String>("This Item Doesn't Exist", HttpStatus.BAD_REQUEST);
        }
    }
}
