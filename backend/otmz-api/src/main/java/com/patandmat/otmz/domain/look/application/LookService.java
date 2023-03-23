package com.patandmat.otmz.domain.look.application;

import com.patandmat.otmz.domain.imageFile.application.ImageFileService;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.item.dto.ItemResponseDto;
import com.patandmat.otmz.domain.item.entity.Item;
import com.patandmat.otmz.domain.look.api.model.LookCountDto;
import com.patandmat.otmz.domain.look.api.model.LookResponseDto;
import com.patandmat.otmz.domain.look.entity.Look;
import com.patandmat.otmz.domain.look.repository.LookRepository;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.domain.member.repository.MemberRepository;
import com.patandmat.otmz.global.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.AttributeNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LookService {

    private final ImageFileService imageFileService;
    private final LookRepository lookRepository;
    private final MemberRepository memberRepository;

    public void saveLook(MultipartFile file, String style, Long id) throws IOException, NoSuchMemberException {
        ImageFile image = imageFileService.save(file);
        String path = image.getPath();
        try {
            Optional<Member> optionalMember = memberRepository.findById(id);
            if (!optionalMember.isPresent()) throw new NoSuchMemberException("No Such Member Exists");
            Member member = optionalMember.get();
            if (member.isDeleted()) throw new NoSuchMemberException("No Such Member Exists");
            Look look = Look.builder()
                    .image(image)
                    .styleVector(style)
                    .member(member)
                    .build();
            lookRepository.save(look);
        } catch (Exception e) {
            imageFileService.delete(image.getId());
            throw e;
        }

    }

    public Page getLooks(Pageable pageable, Long id) throws AttributeNotFoundException, NoSuchMemberException {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (!optionalMember.isPresent()) throw new NoSuchMemberException("No Such Member Exists");
        Member member = optionalMember.get();
        if (member.isDeleted()) throw new NoSuchMemberException("No Such Member Exists");

        Page<Look> page = lookRepository.findByMemberId(id, pageable);
        Page<LookResponseDto> lookDtoPage = page.map(this::convertToLookDto);

        return lookDtoPage;
    }


    public LookResponseDto convertToLookDto(Look look) {

        ImageFile imageFile = look.getImage();
        LookResponseDto lookResponseDto = LookResponseDto.builder()
                .id(look.getId())
                .style(look.getStyle())
                .imageId(imageFile.getId())
                .path(imageFile.getPath())
                .mimeType(imageFile.getMimeType())
                .orignName(imageFile.getOriginName())
                .name(imageFile.getName())
                .build();
        return lookResponseDto;
    }



}
