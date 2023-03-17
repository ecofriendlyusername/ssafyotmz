package com.patandmat.otmz.domain.item.service;

import com.patandmat.otmz.domain.imageFile.application.ImageFileService;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.item.dto.ItemDto;
import com.patandmat.otmz.domain.item.entity.Item;
import com.patandmat.otmz.domain.item.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    public void saveItem(MultipartFile file, ItemDto itemDto) throws IOException, AttributeNotFoundException {
        ImageFile imageFile = imageFileService.save(file);
        String path = imageFile.getPath();
        try {
//            Optional<Member> optionalMember = memberRepository.findById(memberId);
//            Member member = optionalMember.get();
//            if (member == null) throw new NoSuchElementException();
            // Item item = Item.builder().name(name).comment(comment).image(imageFile).build();
            Item item = Item.builder()
                    .name(itemDto.getName())
                    .image(imageFile)
                    .vector(itemDto.getVector())
                    .build();
            itemRepository.save(item);
        } catch (Exception e) {
            imageFileService.delete(path);
            throw e;
        }
    }

    public ItemDto getItem(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (!optionalItem.isPresent()) throw new NoSuchElementException();
        Item item = optionalItem.get();
        ImageFile imageFile = item.getImage();
        try {
            byte[] image = imageFileService.loadData(imageFile.getPath());
            ItemDto itemDto = ItemDto.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .image(image)
                    .vector(item.getVector())
                    .build();
            return itemDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Page getItems(Pageable pageable) {
        Page<Item> page = itemRepository.findAllByName("cute",pageable);
        Page<ItemDto> itemDtoPage = page.map(this::convertToItemDto);
        return itemDtoPage;
    }

    public ItemDto convertToItemDto(Item item) {
        ImageFile imageFile = item.getImage();
        ItemDto itemDto = ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .imageId(imageFile.getId())
                .build();
        return itemDto;
    }


//    private static final Map<String, Integer> fabricToNum;
//    private static final Map<String, Integer> printToNum;
//    private static final Map<String, Integer> categoryToNum;
//
//    private static final String[] numToFabric = {"fur","mouton","suede","angora","corduroy","sequin/glitter","denim","jersey","tweed","velvet","vinyl/pvc","wool/cashmere","synthetic/polyester","knit","lace","linen","mesh","fleece","neoprene","silk","spandex","jacquard","leather","cotton","chiffon"};
//    private static final String[] numToPrint = {"check","stripe","zigzag","leopard","zebra","dot","camouflage","paisley","argyle","floral","lettering","skull","tie-dye","gradation","solid","graphic","Hound's touth","gingham"};
//    private static final String[] numToCategory = {"tops","blouses","casual-tops","knitwear","shirts","vests","coats","jackets","jumpers","paddings","jeans","pants","skirts","dresses","jumpsuits","swimwear"};
//
//    static {
//        categoryToNum = new HashMap<>();
//        categoryToNum.put("tops",0);
//        categoryToNum.put("blouses",1);
//        categoryToNum.put("casual-tops",2);
//        categoryToNum.put("knitwear",3);
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
//        categoryToNum.put("swimwear",15);
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
// }
}
