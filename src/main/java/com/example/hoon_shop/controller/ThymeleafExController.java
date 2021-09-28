package com.example.hoon_shop.controller;

import com.example.hoon_shop.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@RequestMapping(value = "/thymeleaf")
@Controller
public class ThymeleafExController {

    @GetMapping("/ex02")
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
}
