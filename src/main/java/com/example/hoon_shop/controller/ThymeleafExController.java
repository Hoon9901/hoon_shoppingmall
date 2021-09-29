package com.example.hoon_shop.controller;

import com.example.hoon_shop.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/thymeleaf")
@Controller
public class ThymeleafExController {

    @GetMapping("/ex02")    // th:text 예제
    public String thymeleafExample02(Model model) {
        ItemDto itemDto = ItemDto.builder()
                .itemName("테스트 상품1")
                .itemDetail("테스트")
                .price(10000)
                .createdTime(LocalDateTime.now())
                .build();
        model.addAttribute("itemDto", itemDto);
        return "thymeleafEx/thymeleafEx02";
    }

    public void createItemDtoTenToSource(List<ItemDto> source) {
        for (int i = 1; i <= 10; i++) {
            ItemDto itemDto = ItemDto.builder()
                    .itemName("테스트 상품" + i)
                    .itemDetail("상품 상세설명" + i)
                    .price(10000 + i)
                    .createdTime(LocalDateTime.now())
                    .build();

            source.add(itemDto);
        }
    }

    @GetMapping("/ex03")    // th:each (for) 예제
    public String thymeleafExample03(Model model) {
        List<ItemDto> items = new ArrayList<>();

        createItemDtoTenToSource(items);

        model.addAttribute("items", items);
        return "thymeleafEx/thymeleafEx03";
    }

    @GetMapping("/ex04")    // th:if, th:unless(=else)
    public String thymeleafExample04(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<>();

        createItemDtoTenToSource(itemDtoList);

        model.addAttribute("items", itemDtoList);
        return "thymeleafEx/thymeleafEx04";
    }

    @GetMapping("/ex05")
    public String thymeleafExample05() {
        return "thymeleafEx/thymeleafEx05";
    }

    @GetMapping("/ex06")
    public String thymeleafExample06(String param1, String param2, Model model) {
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
        return "thymeleafEx/thymeleafEx06";
    }
}
