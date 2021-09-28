package com.example.hoon_shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {

    private Long id;
    private String itemName;
    private Integer price;
    private String itemDetail;
    private String sellStatus;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    @Builder
    public ItemDto(Long id, String itemName, Integer price, String itemDetail, String sellStatus, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.itemDetail = itemDetail;
        this.sellStatus = sellStatus;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
