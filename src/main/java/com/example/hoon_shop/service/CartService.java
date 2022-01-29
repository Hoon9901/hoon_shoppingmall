package com.example.hoon_shop.service;

import com.example.hoon_shop.dto.CartItemDto;
import com.example.hoon_shop.entity.Cart;
import com.example.hoon_shop.entity.CartItem;
import com.example.hoon_shop.entity.Item;
import com.example.hoon_shop.entity.Member;
import com.example.hoon_shop.repository.CartItemRepository;
import com.example.hoon_shop.repository.CartRepository;
import com.example.hoon_shop.repository.ItemRepository;
import com.example.hoon_shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Long addCartToMember(CartItemDto cartItemDto, String email) {
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(EntityExistsException::new);
        Member member = memberRepository.findByEmail(email);

        Cart cart = cartRepository.findByMemberId(member.getId());
        if (cart == null) {
            cart = createNewCart(member); // 장바구니 생성
        }

        // 상품이 카트에 담겨져있는지 조회
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
        if (savedCartItem != null) {
            savedCartItem.addCount(cartItemDto.getCount()); // 기존 수량에 더함
            return savedCartItem.getId();
        } else {
            // 장바구니에 새로운 상품을 담는다
            return createCartItem(cart, item, cartItemDto.getCount()).getId();
        }
    }

    private Cart createNewCart(Member member) {
        Cart newCart = Cart.createCart(member);
        cartRepository.save(newCart);
        return newCart;
    }

    private CartItem createCartItem(Cart cart, Item item, int count) {
        CartItem newCartItem = CartItem.createCartItem(cart, item, count);
        cartItemRepository.save((newCartItem));
        return newCartItem;
    }
}
