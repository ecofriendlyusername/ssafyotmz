package com.patandmat.otmz.domain.look.application;

import com.patandmat.otmz.domain.imageFile.application.ImageFileService;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.look.api.model.LookResponseDto;
import com.patandmat.otmz.domain.look.entity.Look;
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

@Service
@RequiredArgsConstructor
public class LookService {

    private final ImageFileService imageFileService;
    private final LookRepository lookRepository;

    @Transactional
    public void saveLook(MultipartFile file, String style, Member member) throws NoSuchMemberException {
        ImageFile image = imageFileService.save(file);

        try {
            Look look = Look.builder()
                    .image(image)
                    .styleVector(style)
                    .member(member)
                    .build();
            lookRepository.save(look);
        } catch (Exception e) {
            throw e;
        }

    }

    public Page getLooks(Pageable pageable, Long id) throws AttributeNotFoundException, NoSuchMemberException {
        Page<Look> page = lookRepository.findAllByMemberId(id, pageable);

        return page.map(this::convertToLookDto);
    }


    public LookResponseDto convertToLookDto(Look look) {

        ImageFile imageFile = look.getImage();
        return LookResponseDto.builder()
                .id(look.getId())
                .style(look.getStyle())
                .imageId(imageFile.getId())
                .path(imageFile.getPath())
                .mimeType(imageFile.getMimeType())
                .orignName(imageFile.getOriginName())
                .name(imageFile.getName())
                .build();
    }


    public int getCountoflooks(Long id) {
        return lookRepository.countByMemberId(id);
    }
}
