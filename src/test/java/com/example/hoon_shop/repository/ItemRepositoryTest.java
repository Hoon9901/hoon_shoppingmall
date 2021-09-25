package com.example.hoon_shop.repository;

import com.example.hoon_shop.constant.ItemSellStatus;
import com.example.hoon_shop.entity.Item;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @AfterEach
    public void cleanup() {
        itemRepository.deleteAll();
    }

    @Test
    public void 상품_저장_테스트() {
        Item item = new Item();
        item.setItemName("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setCreateTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        Item savedItem = itemRepository.save(item);

        assertThat(savedItem).isEqualTo(item);
    }

    public void createItemList() {
        for(int i = 0; i < 10; i++){
            Item item = new Item();
            item.setItemName("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setCreateTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    public void 상품명_조회_테스트() {
        this.createItemList();
        List<Item> items = itemRepository.findByItemName("테스트 상품0");

        assertThat(items.get(0).getItemName()).isEqualTo("테스트 상품0");
    }

    @Test
    public void 상품명_또는_상품상세설명_조회_테스트() {
        this.createItemList();
        List<Item> items = itemRepository.findByItemNameOrItemDetail("테스트 상품0", "테스트 상품 상세 설명9");

        assertThat(items.get(0).getItemName()).isEqualTo("테스트 상품0");
        assertThat(items.get(1).getItemName()).isEqualTo("테스트 상품9");
    }

    @Test
    public void 상품가격_LessThan_테스트() {
        this.createItemList();
        List<Item> items = itemRepository.findByPriceLessThan(10004);
        for (Item item : items) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void 상품가격_LessThan_내림차순_테스트() {
        this.createItemList();
        List<Item> items = itemRepository.findByPriceLessThanOrderByPriceDesc(10004);
        for (Item item : items) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void Query를_이용한_상품조회_테스트() {
        this.createItemList();
        List<Item> items = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for (Item item : items) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void NativeQuery를_이용한_상품조회() {
        this.createItemList();
        List<Item> items = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for (Item item : items) {
            System.out.println(item.toString());
        }
    }
}