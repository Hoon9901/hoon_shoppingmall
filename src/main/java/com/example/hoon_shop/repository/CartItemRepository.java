package com.example.hoon_shop.repository;

import com.example.hoon_shop.dto.CartItemDto;
import com.example.hoon_shop.entity.CartItem;
import org.apache.commons.logging.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

}