package com.example.hoon_shop.dto;

import com.example.hoon_shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {
    // 상품 조회 조건 Dto
    private String searchDateType;  // 현재 시간과 등록일 비교 조회

    private ItemSellStatus searchSellStatus; // 판매상태 기준으로 조회

    private String searchBy; // 임의 유형으로 조회

    private String searchQuery = ""; // 조회할 검색어 저장

}
