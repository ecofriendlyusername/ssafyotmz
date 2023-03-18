package com.patandmat.otmz.domain.item.service;

import com.patandmat.otmz.domain.imageFile.application.ImageFileService;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.item.dto.ItemDto;
import com.patandmat.otmz.domain.item.entity.ItemMatch;
import com.patandmat.otmz.domain.item.dto.ItemMatchDto;
import com.patandmat.otmz.domain.item.repository.ItemMatchRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.AttributeNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemMatchService {
    private final ImageFileService imageFileService;
    private final ItemMatchRepository itemMatchRepository;

    @Transactional
    public void saveItemMatch(MultipartFile file, ItemMatch itemMatch) throws IOException, AttributeNotFoundException {
        ImageFile imageFile = imageFileService.save(file);
        String path = imageFile.getPath();
        try {
            itemMatch.setImage(imageFile);
            itemMatchRepository.save(itemMatch);
        } catch (Exception e) {
            e.printStackTrace();
            imageFileService.delete(path);
            throw e;
        }
    }

    public ItemMatchDto getItemMatch(Long id) {
        Optional<ItemMatch> optionalItem = itemMatchRepository.findById(id);
        if (!optionalItem.isPresent()) throw new NoSuchElementException();
        ItemMatch itemMatch = optionalItem.get();
        ImageFile imageFile = itemMatch.getImage();
        try {
            byte[] image = imageFileService.loadData(imageFile.getPath());
            ItemMatchDto itemMatchDto = ItemMatchDto.builder()
                    .id(itemMatch.getId())
                    .name(itemMatch.getName())
                    .comment(itemMatch.getComment())
                    .image(image)
                    .build();
            return itemMatchDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Page getItemMatchPage(Pageable pageable) {
        Page<ItemMatch> page = itemMatchRepository.findAll(pageable);
        Page<ItemMatchDto> itemMatchDtoPage = page.map(this:: convertToItemMatchDto);
        return itemMatchDtoPage;
    }

    public ItemMatchDto convertToItemMatchDto(ItemMatch itemMatch) {
        ImageFile imageFile = itemMatch.getImage();
        ItemMatchDto itemMatchDto = ItemMatchDto.builder()
                .id(itemMatch.getId())
                .name(itemMatch.getName())
                .imageId(imageFile.getId())
                .comment(itemMatch.getComment())
                .build();
        return itemMatchDto;
    }
}
