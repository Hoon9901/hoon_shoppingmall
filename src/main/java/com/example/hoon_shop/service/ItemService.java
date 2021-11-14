package com.example.hoon_shop.service;


import com.example.hoon_shop.dto.ItemFormDto;
import com.example.hoon_shop.dto.ItemImgDto;
import com.example.hoon_shop.entity.Item;
import com.example.hoon_shop.entity.ItemImg;
import com.example.hoon_shop.repository.ItemImgRepository;
import com.example.hoon_shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRespotiory;

    public Long saveItem(ItemFormDto itemFormDto,
                         List<MultipartFile> itemImgFileList) throws Exception {

        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        // 이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if (i == 0) {
                itemImg.setRepImgYn("Y");
            } else {
                itemImg.setRepImgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemFormDto getItemDetail(Long itemId) {

        List<ItemImg> itemImgs = itemImgRespotiory.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtos = new ArrayList<>();
        for (ItemImg itemImg : itemImgs) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtos.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtos(itemImgDtos);
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFiles) throws Exception {

        // 상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);

        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        // 이미지 등록
        for (int i = 0; i < itemImgFiles.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFiles.get(i));
        }

        return item.getId();
    }
}
