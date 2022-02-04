package com.example.hoon_shop.dto;

import com.example.hoon_shop.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

@Getter
@Setter
public class OrderItemDto {

    private String itemName; // 상품명

    private int count;  // 주문 수량

    private int orderPrice; // 주문 금액

    private String imgUrl;  // 상품 이미지 경로

    public OrderItemDto(){}
    public OrderItemDto(String itemName, int count, int orderPrice, String imgUrl) {
        this.itemName = itemName;
        this.count = count;
        this.orderPrice = orderPrice;
        this.imgUrl = imgUrl;
    }

    public static OrderItemDto of(OrderItem orderItem, String imgUrl) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.itemName = orderItem.getItem().getItemName();
        orderItemDto.count = orderItem.getCount();
        orderItemDto.orderPrice = orderItem.getOrderPrice();
        orderItemDto.imgUrl = imgUrl;
        return orderItemDto;
    }
}
