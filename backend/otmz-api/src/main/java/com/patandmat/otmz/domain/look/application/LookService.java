package com.patandmat.otmz.domain.look.application;

import com.patandmat.otmz.domain.imageFile.application.ImageFileService;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.item.dto.ItemResponseDto;
import com.patandmat.otmz.domain.look.api.model.LookResponse;
import com.patandmat.otmz.domain.look.api.model.SurveyStyleResponse;
import com.patandmat.otmz.domain.look.dto.LookResponseDto;
import com.patandmat.otmz.domain.look.entity.Look;
import com.patandmat.otmz.domain.look.entity.Style;
import com.patandmat.otmz.domain.look.repository.LookRepository;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.domain.member.repository.MemberRepository;
import com.patandmat.otmz.global.exception.NoSuchMemberException;
import com.patandmat.otmz.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.management.AttributeNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LookService {

    private final ImageFileService imageFileService;
    private final LookRepository lookRepository;

    private final MemberRepository memberRepository;

    @Transactional
    @CacheEvict(value = "recommendItem", key = "#member.id")
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

    public List<LookResponse> findByMemberId(Member member, Pageable pageable) {
        return lookRepository.findAllByMemberId(member.getId(), pageable)
                .stream()
                .map(look -> LookResponse.builder()
                        .id(look.getId())
                        .memberId(member.getId())
                        .ownerName(member.getNickname())
                        .style(look.getStyle().getKey())
                        .imageId(look.getImage().getId())
                        .build()).toList();
    }

    public List<SurveyStyleResponse> getLooksByStyle(Style style) {
        List<Look> loks = lookRepository.findTop2ByStyle(style);
        return lookRepository.findTop2ByStyle(style)
                             .stream()
                             .map(look -> new SurveyStyleResponse(look.getStyle().getKey(), look.getImage().getId()))
                             .collect(Collectors.toList());
    }

    @Transactional
    public void deleteLook(Long id, Long memberId) throws UnauthorizedException, IOException {
        Optional<Look> optionalItem = lookRepository.findById(id);
        if (!optionalItem.isPresent()) return;
        Look look = optionalItem.get();
        Member member = look.getMember();
        if (member == null || member.getId() != memberId) throw new UnauthorizedException();
        lookRepository.delete(look);
        ImageFile imageFile = look.getImage();
        imageFileService.delete(imageFile.getId());
    }

    public LookResponseDto getLook(Long look_id) {
        Optional<Look> optionalLook = lookRepository.findById(look_id);
        if (!optionalLook.isPresent()) throw new NoSuchElementException();
        Look look = optionalLook.get();
        Member member = look.getMember();
        String uploader = member == null ? "없는 회원" : member.getNickname();
        try {
            LookResponseDto lookResponseDto = LookResponseDto.builder()
                    .style(look.getStyle().getKey())
                    .uploader(uploader)
                    .build();
            System.out.println(look.getStyle().getKey());
            return lookResponseDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
