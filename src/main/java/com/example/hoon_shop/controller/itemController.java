package com.example.hoon_shop.controller;

import com.example.hoon_shop.dto.ItemFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class itemController {

    @GetMapping("/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }
}
