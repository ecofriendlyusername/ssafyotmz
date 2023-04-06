package com.patandmat.otmz.domain.item.application;

import com.patandmat.otmz.domain.imageFile.application.ImageFileService;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.item.dto.ItemResponseDto;
import com.patandmat.otmz.domain.item.dto.ItemRequestDto;
import com.patandmat.otmz.domain.item.dto.ItemResponsePageDto;
import com.patandmat.otmz.domain.item.entity.Item;

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

            int categoryNum = categoryToNum.getOrDefault(category, -1);
            int styleNum = styleToNum.getOrDefault(style, -1);

            if (categoryNum == -1 || styleNum == -1) throw new AttributeNotFoundException();

            Item item = Item.builder()
                    .name(itemRequestDto.getName())
                    .image(imageFile)
                    .styleVector(itemRequestDto.getStyleVector())
                    .color(itemRequestDto.getColor())
                    .style(styleNum)
                    .category(categoryNum)
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
                    .category(numToCategory[item.getCategory()])
                    .color(item.getColor())
                    .category(numToCategory[item.getCategory()])
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

        int categoryNum = categoryToNum.getOrDefault(category, -1);
        if (categoryNum == -1) throw new AttributeNotFoundException();
        Page<Item> page = itemRepository.findAllByCategoryAndMemberId(categoryNum, id, pageable);
        Page<ItemResponsePageDto> itemResponseDtoPage = page.map(this::convertToItemResponsePageDto);
        return itemResponseDtoPage;
    }

    public Map<String,Integer> countByStyle(Long id) {
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i < numToStyle.length; i++) {
            map.put(numToStyle[i],itemRepository.countByMemberIdAndStyle(id,i));
        }
        return map;
    }

    public ItemResponsePageDto convertToItemResponsePageDto(Item item) {
        ImageFile imageFile = item.getImage();
        ItemResponsePageDto itemResponsePageDto = ItemResponsePageDto.builder()
                .id(item.getId())
                .name(item.getName())
                .category(numToCategory[item.getCategory()])
                .imageId(imageFile.getId())
                .build();
        return itemResponsePageDto;
    }

    //    private static final Map<String, Integer> fabricToNum;
//    private static final Map<String, Integer> printToNum;
    private static final Map<String, Integer> categoryToNum;
    private static final Map<String, Integer> styleToNum;
    //
//    private static final String[] numToFabric = {"fur","mouton","suede","angora","corduroy","sequin/glitter","denim","jersey","tweed","velvet","vinyl/pvc","wool/cashmere","synthetic/polyester","knit","lace","linen","mesh","fleece","neoprene","silk","spandex","jacquard","leather","cotton","chiffon"};
//    private static final String[] numToPrint = {"check","stripe","zigzag","leopard","zebra","dot","camouflage","paisley","argyle","floral","lettering","skull","tie-dye","gradation","solid","graphic","Hound's touth","gingham"};
// private static final String[] numToCategory = {"tops","blouses","casual-tops","knitwear","shirts","vests","coats","jackets","jumpers","paddings","jeans","pants","skirts","dresses","jumpsuits","swimwear"};
    private static final String[] numToCategory = {"outer", "upper", "lower", "dress", "etc"};

    private static final String[] numToStyle = {"street","sporty","kitsch","sexy","romantic","hippie","preppy","retro","resort","classic","military","modern","hiphop","feminine","tomboy","country","manish","genderless","avantgarde","oriental","punk","sophisticated","western"};

    static {
        styleToNum = new HashMap<>();
        styleToNum.put("street",0);
        styleToNum.put("sporty",1);
        styleToNum.put("kitsch",2);
        styleToNum.put("sexy",3);
        styleToNum.put("romantic",4);
        styleToNum.put("hippie",5);
        styleToNum.put("preppy",6);
        styleToNum.put("retro",7);
        styleToNum.put("resort",8);
        styleToNum.put("classic",9);
        styleToNum.put("military",10);
        styleToNum.put("modern",11);
        styleToNum.put("hiphop",12);
        styleToNum.put("feminine",13);
        styleToNum.put("tomboy",14);
        styleToNum.put("country",15);
        styleToNum.put("manish",16);
        styleToNum.put("genderless",17);
        styleToNum.put("avantgarde",18);
        styleToNum.put("oriental",19);
        styleToNum.put("punk",20);
        styleToNum.put("sophisticated",21);
        styleToNum.put("western",22);

        categoryToNum = new HashMap<>();
        categoryToNum.put("outer", 0);
        categoryToNum.put("upper", 1);
        categoryToNum.put("lower", 2);
        categoryToNum.put("dress", 3);
        categoryToNum.put("etc", 4);
//        categoryToNum.put("shirts",4);
//        categoryToNum.put("vests",5);
//        categoryToNum.put("coats",6);
//        categoryToNum.put("jackets",7);
//        categoryToNum.put("jumpers",8);
//        categoryToNum.put("paddings",9);
//        categoryToNum.put("jeans",10);
//        categoryToNum.put("pants",11);
//        categoryToNum.put("skirts",12);
//        categoryToNum.put("dresses",13);
//        categoryToNum.put("jumpsuits",14);
//        categoryToNum.put("swimwear",15);{
//"" : 2.3
//}
//
//        printToNum = new HashMap<>();
//        printToNum.put("check",0);
//        printToNum.put("stripe",1);
//        printToNum.put("zigzag",2);
//        printToNum.put("leopard",3);
//        printToNum.put("zebra",4);
//        printToNum.put("dot",5);
//        printToNum.put("camouflage",6);
//        printToNum.put("paisley",7);
//        printToNum.put("argyle",8);
//        printToNum.put("floral",9);
//        printToNum.put("lettering",10);
//        printToNum.put("skull",11);
//        printToNum.put("tie-dye",12);
//        printToNum.put("gradation",13);
//        printToNum.put("solid",14);
//        printToNum.put("graphic",15);
//        printToNum.put("Hound's touth",16);
//        printToNum.put("gingham",17);
//
//        fabricToNum = new HashMap<>();
//        fabricToNum.put("fur",0);
//        fabricToNum.put("mouton",1);
//        fabricToNum.put("suede",2);
//        fabricToNum.put("angora",3);
//        fabricToNum.put("corduroy",4);
//        fabricToNum.put("sequin/glitter",5);
//        fabricToNum.put("denim",6);
//        fabricToNum.put("jersey",7);
//        fabricToNum.put("tweed",8);
//        fabricToNum.put("velvet",9);
//        fabricToNum.put("vinyl/pvc",10);
//        fabricToNum.put("wool/cashmere",11);
//        fabricToNum.put("synthetic/polyester",12);
//        fabricToNum.put("knit",13);
//        fabricToNum.put("lace",14);
//        fabricToNum.put("linen",15);
//        fabricToNum.put("mesh",16);
//        fabricToNum.put("fleece",17);
//        fabricToNum.put("neoprene",18);
//        fabricToNum.put("silk",19);
//        fabricToNum.put("spandex",20);
//        fabricToNum.put("jacquard",21);
//        fabricToNum.put("leather",22);
//        fabricToNum.put("cotton",23);
//        fabricToNum.put("chiffon",24);
    }
}
