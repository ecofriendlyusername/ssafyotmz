package com.patandmat.otmz.domain.look.application;

import com.patandmat.otmz.domain.imageFile.application.ImageFileService;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.look.entity.Look;
import com.patandmat.otmz.domain.look.entity.Style;
import com.patandmat.otmz.domain.look.repository.LookRepository;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.global.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.management.AttributeNotFoundException;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LookService {

    private final ImageFileService imageFileService;
    private final LookRepository lookRepository;

    @Transactional
    public Look saveLook(MultipartFile file, String styleVector, String style, Member member) throws NoSuchMemberException {
        ImageFile image = imageFileService.save(file);

        try {
            Look look = Look.builder()
                    .image(image)
                    .styleVector(styleVector)
                    .style(Style.valueOf(style.toUpperCase()))
                    .member(member)
                    .build();
            lookRepository.save(look);

            return look;
        } catch (Exception e) {
            throw e;
        }
    }

    public Page<Look> getLooksWithPageable(String style, Pageable pageable) throws AttributeNotFoundException, NoSuchMemberException {
        return lookRepository.findAllByStyleOrderByCreatedAtDesc(Style.valueOf(style.toUpperCase()), pageable);
    }
}
