package com.patandmat.otmz.domain.item.application;

import com.patandmat.otmz.domain.imageFile.application.ImageFileService;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.item.dto.ItemMatchRequestDto;
import com.patandmat.otmz.domain.item.entity.ItemMatch;
import com.patandmat.otmz.domain.item.dto.ItemMatchResponseDto;

import com.patandmat.otmz.domain.item.repository.ItemMatchRepository;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.domain.member.repository.MemberRepository;
import com.patandmat.otmz.global.exception.NoSuchMemberException;
import com.patandmat.otmz.global.exception.UnauthorizedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.AttributeNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemMatchService {
    private final ImageFileService imageFileService;
    private final ItemMatchRepository itemMatchRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveItemMatch(MultipartFile file, ItemMatchRequestDto itemMatchRequestDto, Long id) throws IOException, AttributeNotFoundException, NoSuchMemberException {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (!optionalMember.isPresent()) throw new NoSuchMemberException("No Such Member Exists");
        Member member = optionalMember.get();
        if (member.isDeleted()) throw new NoSuchMemberException("No Such Member Exists");
        ImageFile imageFile = imageFileService.save(file);
        try {
            ItemMatch itemMatch = ItemMatch.builder().
                    image(imageFile)
                    .name(itemMatchRequestDto.getName())
                    .comment(itemMatchRequestDto.getComment())
                    .member(member)
                    .build();
            itemMatchRepository.save(itemMatch);
        } catch (Exception e) {
            e.printStackTrace();
            imageFileService.delete(imageFile.getId());
            throw e;
        }
    }

    public ItemMatchResponseDto getItemMatch(Long id, Long member_id) throws NoSuchMemberException, UnauthorizedException {
        Optional<Member> optionalMember = memberRepository.findById(member_id);
        if (!optionalMember.isPresent()) throw new NoSuchMemberException("No Such Member Exists");
        Member member = optionalMember.get();
        if (member.isDeleted()) throw new NoSuchMemberException("No Such Member Exists");
        Optional<ItemMatch> optionalItem = itemMatchRepository.findById(id);
        if (!optionalItem.isPresent()) throw new NoSuchElementException();
        ItemMatch itemMatch = optionalItem.get();
        if (itemMatch.getMember().getId() != member_id) throw new UnauthorizedException();
        ImageFile imageFile = itemMatch.getImage();
        try {
            byte[] image = imageFileService.loadData(imageFile.getPath());
            ItemMatchResponseDto itemMatchResponseDto = ItemMatchResponseDto.builder()
                    .id(itemMatch.getId())
                    .name(itemMatch.getName())
                    .comment(itemMatch.getComment())
                    .image(image)
                    .build();
            return itemMatchResponseDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Page getItemMatchPage(Pageable pageable, Long member_id) throws NoSuchMemberException {
        Optional<Member> optionalMember = memberRepository.findById(member_id);
        if (!optionalMember.isPresent()) throw new NoSuchMemberException("No Such Member Exists");
        Member member = optionalMember.get();
        if (member.isDeleted()) throw new NoSuchMemberException("No Such Member Exists");
        Page<ItemMatch> page = itemMatchRepository.findAllByMemberId(member_id,pageable);
        Page<ItemMatchResponseDto> itemMatchDtoPage = page.map(this:: convertToItemMatchDto);
        return itemMatchDtoPage;
    }

    public ItemMatchResponseDto convertToItemMatchDto(ItemMatch itemMatch) {
        ImageFile imageFile = itemMatch.getImage();
        ItemMatchResponseDto itemMatchResponseDto = ItemMatchResponseDto.builder()
                .id(itemMatch.getId())
                .name(itemMatch.getName())
                .imageId(imageFile.getId())
                .comment(itemMatch.getComment())
                .build();
        return itemMatchResponseDto;
    }

    public void deleteItemMatch(Long id, Long memberId) throws NoSuchMemberException, IOException {
        Optional<ItemMatch> itemMatchOptional = itemMatchRepository.findById(id);
        if (!itemMatchOptional.isPresent()) return;
        ItemMatch itemMatch = itemMatchOptional.get();
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (!optionalMember.isPresent()) throw new NoSuchMemberException("No Such Member Exists");
        Member member = optionalMember.get();
        if (member.isDeleted()) throw new NoSuchMemberException("No Such Member Exists");
        ImageFile imageFile = itemMatch.getImage();
        itemMatchRepository.delete(itemMatchOptional.get());
        imageFileService.delete(imageFile.getId());
    }

    public void deleteMultipleItemMatches(List<Long> ids, Long memberId) throws NoSuchMemberException, UnauthorizedException {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (!optionalMember.isPresent()) throw new NoSuchMemberException("No Such Member Exists");
        Member member = optionalMember.get();
        if (member.isDeleted()) throw new NoSuchMemberException("No Such Member Exists");
        List<ItemMatch> itemMatchList = itemMatchRepository.findAllById(ids);
        for (ItemMatch itemMatch : itemMatchList) {
            if (itemMatch.getMember().getId() != memberId) throw new UnauthorizedException();
            ImageFile imageFile = itemMatch.getImage();
            itemMatchRepository.delete(itemMatch);
            try {
                imageFileService.delete(imageFile.getId());
            } catch (IOException e) {
                // throw new RuntimeException(e);
            }
        }
    }
}
