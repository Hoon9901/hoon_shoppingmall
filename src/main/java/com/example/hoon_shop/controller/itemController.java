package com.example.hoon_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class itemController {

    @GetMapping("/admin/item/new")
    public String itemForm() {
        return "/item/itemForm";
    }
}
