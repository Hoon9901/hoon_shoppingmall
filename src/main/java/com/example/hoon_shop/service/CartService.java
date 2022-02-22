package com.example.hoon_shop.service;

import com.example.hoon_shop.dto.CartDetailDto;
import com.example.hoon_shop.dto.CartItemDto;
import com.example.hoon_shop.dto.CartOrderDto;
import com.example.hoon_shop.dto.OrderDto;
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
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    private final OrderService orderService;

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

    public Long orderCartItem(List<CartOrderDto> cartOrderDtos, String email) {
        List<OrderDto> orderDtos = new ArrayList<>();
        for (CartOrderDto cartOrderDto : cartOrderDtos) {
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setCount(cartItem.getCount());
            orderDtos.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtos, email);

        for (CartOrderDto cartOrderDto : cartOrderDtos) {
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }

        return orderId;
    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email) {
        List<CartDetailDto> cartDetailDtos = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getId());
        // 장바구니 엔티티 존재하지 않음
        if (cart == null) {
            return cartDetailDtos;
        }
        // 장바구니에 담긴 상품 정보를 조회한다.
        cartDetailDtos = cartItemRepository.findCartDetailDtoList(cart.getId());
        return cartDetailDtos;
    }

    // 요청한 회원과 카트 회원이 같은지 검증
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email) {
        Member currentMember = memberRepository.findByEmail(email); // 요청 회원
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember(); // 조회한 카트의 회원

        if (!StringUtils.equals(currentMember.getEmail(), savedMember.getEmail())) {
            return false;
        }

        return true;
    }

    public void updateCartItemCount(Long cartItemId, int count) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItem.updateCount(count);
    }

    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }
}
