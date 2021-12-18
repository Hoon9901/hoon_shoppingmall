package com.example.hoon_shop.service;

import com.example.hoon_shop.constant.ItemSellStatus;
import com.example.hoon_shop.dto.ItemFormDto;
import com.example.hoon_shop.entity.Item;
import com.example.hoon_shop.entity.ItemImg;
import com.example.hoon_shop.repository.ItemImgRepository;
import com.example.hoon_shop.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource("classpath:application-test.properties")
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    List<MultipartFile> createMultipartFiles() throws Exception {
        List<MultipartFile> multipartFiles = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            String path = "./shop/item/";
            String imageName = "셔츠" + i + ".jpg";
            MockMultipartFile mockMultipartFile =
                    new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1, 2, 3, 4});
            multipartFiles.add(mockMultipartFile);
        }
        return multipartFiles;
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void 상품_등록_테스트() throws Exception {
        ItemFormDto itemFormDto = new ItemFormDto();
        itemFormDto.setItemName("테스트상품");
        itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
        itemFormDto.setItemDetail("테스트 상품 임.");
        itemFormDto.setPrice(1000);
        itemFormDto.setStockNumber(100);

        List<MultipartFile> multipartFiles = createMultipartFiles();
        Long itemId = itemService.saveItem(itemFormDto, multipartFiles);

        List<ItemImg> itemImgs = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(itemFormDto.getItemName(), item.getItemName());
        assertEquals(itemFormDto.getItemSellStatus(), item.getItemSellStatus());
        assertEquals(itemFormDto.getItemDetail(), item.getItemDetail());
        assertEquals(itemFormDto.getPrice(), item.getPrice());
        assertEquals(itemFormDto.getStockNumber(), item.getStockNumber());
        assertEquals(multipartFiles.get(0).getOriginalFilename(), itemImgs.get(0).getOrgImgName());
    }
}