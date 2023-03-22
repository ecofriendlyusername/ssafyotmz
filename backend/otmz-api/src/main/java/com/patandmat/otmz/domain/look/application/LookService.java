package com.patandmat.otmz.domain.look.application;

import com.patandmat.otmz.domain.imageFile.application.ImageFileService;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.item.entity.Item;
import com.patandmat.otmz.domain.item.exception.NoSuchMemberException;
import com.patandmat.otmz.domain.look.entity.Look;
import com.patandmat.otmz.domain.look.repository.LookRepository;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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

    public void saveLook(MultipartFile file, String style, Long id)throws IOException, AttributeNotFoundException, NoSuchMemberException{
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
            imageFileService.delete(path);
            throw e;
        }

    }
}
