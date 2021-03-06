package com.example.hoon_shop.repository;

import com.example.hoon_shop.dto.ItemSearchDto;
import com.example.hoon_shop.dto.MainItemDto;
import com.example.hoon_shop.entity.Item;
import org.jboss.jandex.Main;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
