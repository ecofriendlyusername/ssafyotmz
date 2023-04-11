package com.patandmat.otmz.domain.item.application;

import com.patandmat.otmz.domain.imageFile.application.ImageFileService;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.item.dto.ItemResponseDto;
import com.patandmat.otmz.domain.item.dto.ItemRequestDto;
import com.patandmat.otmz.domain.item.dto.ItemResponsePageDto;
import com.patandmat.otmz.domain.item.entity.Item;

import com.patandmat.otmz.domain.item.entity.ItemCat;
import com.patandmat.otmz.domain.item.entity.ItemStyle;
import com.patandmat.otmz.domain.item.repository.ItemRepository;
import com.patandmat.otmz.domain.member.application.MemberService;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.domain.member.repository.MemberRepository;
import com.patandmat.otmz.global.exception.NoSuchMemberException;
import com.patandmat.otmz.global.exception.UnauthorizedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.AttributeNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ImageFileService imageFileService;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private final MemberService memberService;

    @Transactional
    @CacheEvict(value = "recommendItem", key = "#id")
    public void saveItem(MultipartFile file, ItemRequestDto itemRequestDto, String category, String style, Long id) throws IOException, AttributeNotFoundException, NoSuchMemberException {
        ImageFile imageFile = imageFileService.save(file);
        try {
            Optional<Member> optionalMember = memberRepository.findById(id);
            if (!optionalMember.isPresent()) throw new NoSuchMemberException("No Such Member Exists");
            Member member = optionalMember.get();

            if (member.isDeleted()) throw new NoSuchMemberException("No Such Member Exists");

            ItemCat itemCat = ItemCat.valueOf(category.toUpperCase());
            ItemStyle itemStyle = ItemStyle.valueOf(style.toUpperCase());

            if (itemCat == null || itemStyle == null) throw new AttributeNotFoundException();

            Item item = Item.builder()
                    .name(itemRequestDto.getName())
                    .image(imageFile)
                    .styleVector(itemRequestDto.getStyleVector())
                    .color(itemRequestDto.getColor())
                    .style(itemStyle)
                    .category(itemCat)
                    .member(member)
                    .build();
            itemRepository.save(item);
            memberService.updateItemStyleStat(member, item.getStyleVector());
        } catch (Exception e) {
            imageFileService.delete(imageFile.getId());
            throw e;
        }
    }

    public ItemResponseDto getItem(Long item_id, Long member_id) {
        Optional<Item> optionalItem = itemRepository.findById(item_id);
        if (!optionalItem.isPresent()) throw new NoSuchElementException();
        Item item = optionalItem.get();
        try {
            Optional<Member> optionalMember = memberRepository.findById(member_id);
            if (!optionalMember.isPresent()) throw new NoSuchMemberException("No Such Member Exists");
            Member member = optionalMember.get();
            if (member.isDeleted()) throw new NoSuchMemberException("No Such Member Exists");
            ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .category(item.getCategory().name())
                    .color(item.getColor())
                    .build();
            return itemResponseDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteItem(Long id, Long memberId) throws UnauthorizedException, IOException {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (!optionalItem.isPresent()) return;
        Item item = optionalItem.get();
        Member member = item.getMember();
        if (member == null || member.getId() != memberId) throw new UnauthorizedException();
        itemRepository.delete(item);
        ImageFile imageFile = item.getImage();
        imageFileService.delete(imageFile.getId());
    }

    public void deleteMultipleItems(List<Long> ids, Long memberId) throws NoSuchMemberException, UnauthorizedException {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (!optionalMember.isPresent()) throw new NoSuchMemberException("No Such Member Exists");
        Member member = optionalMember.get();
        if (member.isDeleted()) throw new NoSuchMemberException("No Such Member Exists");
        List<Item> itemMatchList = itemRepository.findAllById(ids);
        for (Item item : itemMatchList) {
            if (item.getMember().getId() != memberId) throw new UnauthorizedException();
            ImageFile imageFile = item.getImage();
            itemRepository.delete(item);
            try {
                imageFileService.delete(imageFile.getId());
            } catch (IOException e) {
                // throw new RuntimeException(e);
            }
        }
    }

    public Page getItems(Pageable pageable, String category, Long id) throws AttributeNotFoundException, NoSuchMemberException {
//        Optional<Member> optionalMember = memberRepository.findById(id);
//        if (!optionalMember.isPresent()) throw new NoSuchMemberException("No Such Member Exists");
//        Member member = optionalMember.get();
//        if (member.isDeleted()) throw new NoSuchMemberException("No Such Member Exists");

        ItemCat itemCat = ItemCat.valueOf(category.toUpperCase());
        if (itemCat == null) throw new AttributeNotFoundException();
        Page<Item> page = itemRepository.findAllByCategoryAndMemberId(itemCat, id, pageable);
        Page<ItemResponsePageDto> itemResponseDtoPage = page.map(this::convertToItemResponsePageDto);
        return itemResponseDtoPage;
    }

    public Map<String,Integer> countByStyle(Long id) {
        Map<String,Integer> map = new HashMap<>();
        for (ItemStyle itemStyle : ItemStyle.values()) {
            map.put(itemStyle.name(),itemRepository.countByMemberIdAndStyle(id,itemStyle));
        }
        return map;
    }

    public ItemResponsePageDto convertToItemResponsePageDto(Item item) {
        ImageFile imageFile = item.getImage();
        ItemResponsePageDto itemResponsePageDto = ItemResponsePageDto.builder()
                .id(item.getId())
                .name(item.getName())
                .category(item.getCategory().name())
                .imageId(imageFile.getId())
                .build();
        return itemResponsePageDto;
    }
}
