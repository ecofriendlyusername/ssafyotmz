package com.patandmat.otmz.domain.item.application;

import com.patandmat.otmz.domain.imageFile.application.ImageFileService;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.imageFile.repository.ImageFileRepository;
import com.patandmat.otmz.domain.item.entity.Item;
import com.patandmat.otmz.domain.item.repository.ItemRepository;
import com.patandmat.otmz.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InitItemCopyService {
    @Value("${file.image.path}")
    private String absolutePath;
    private final ImageFileRepository imageFileRepository;
    private final ItemRepository itemRepository;
    private final ImageFileService imageFileService;

    private final String[] pathsForStyle = {"outer.png","upper.png","lower.png","dress.png","etc.png"};
    @Transactional
    public ImageFile copyItemsForThisUser(Long member_id, String imgPath) throws FileNotFoundException {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(absolutePath + imgPath);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("해당 파일을 찾을 수 없습니다.");
        }

        String type = imgPath.split("\\.")[1];

        String name = "tempDataFor" + member_id + UUID.randomUUID() + "." + type;

        ImageFile imageFile = ImageFile.builder()
//                .name(name)
//                .length(4L)
//                .mineType("str")
//                .originName("str")
                .path(name)
                .build();

        File file = new File(absolutePath + imageFile.getPath());

        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        imageFileRepository.save(imageFile);

        return imageFile;
    }
    public void setTestItemsForThisUser(Member joinedMember) throws IOException {
        for (int i = 0; i < pathsForStyle.length; i++) {
            setTestItem(joinedMember,i,pathsForStyle[i]);
        }
    }

    @Transactional
    public void setTestItem(Member member, int cat, String path) throws IOException {
        String styleVector = "";
        ImageFile imageFile = copyItemsForThisUser(member.getId(),path);
        try {
            Item item = Item.builder()
                    .name(cat + " for test")
                    .image(imageFile)
                    .styleVector(styleVector)
                    .color("red")
                    .style(1)
                    .category(cat)
                    .member(member)
                    .build();
            itemRepository.save(item);
//            memberService.updateItemStyleStat(joinedMember, item.getStyleVector());
        } catch (Exception e) {
            // Referential integrity constraint violation: "FK93OT5HBMO218M7VVC23V7OSL8: PUBLIC.ITEM FOREIGN KEY(IMAGE_ID) REFERENCES PUBLIC.IMAGE_FILE(ID) without @Transactional
            imageFileService.delete(imageFile.getId());
            throw new RuntimeException(e);
        }
    }
}
