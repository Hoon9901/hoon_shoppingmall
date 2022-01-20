package com.example.hoon_shop.dto;

import com.example.hoon_shop.constant.OrderStatus;
import com.example.hoon_shop.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistoryDto {

    private Long orderId;

    private String orderDate;

    private OrderStatus orderStatus;

    // 주문 상품 리스트
    private List<OrderItemDto> orderItemDtos = new ArrayList<>();

    public OrderHistoryDto(){

    }

    public OrderHistoryDto(Long orderId, String orderDate, OrderStatus orderStatus, List<OrderItemDto> orderItemDtos) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderItemDtos = orderItemDtos;
    }

    public static OrderHistoryDto of(Order order) {
        OrderHistoryDto orderHistoryDto = new OrderHistoryDto();
        orderHistoryDto.orderId = order.getId();
        orderHistoryDto.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        orderHistoryDto.orderStatus = order.getOrderStatus();
        return orderHistoryDto;
    }

    public void addOrderItemDto(OrderItemDto orderItemDto) {
        orderItemDtos.add(orderItemDto);
    }

}
