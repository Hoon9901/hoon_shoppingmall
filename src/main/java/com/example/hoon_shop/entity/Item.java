package com.example.hoon_shop.entity;

import com.example.hoon_shop.constant.ItemSellStatus;
import com.example.hoon_shop.dto.ItemFormDto;
import com.example.hoon_shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter // TODO : 빌더 패턴으로 작성하기
@ToString
@Table(name = "item")
@Entity
public class Item extends BaseEntity {

    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;    // 상품 코드

    @Column(nullable = false, length = 50)
    private String itemName;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber; // 재고 수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; // 상품 상세설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;  // 상품 판매 상태

    public void updateItem(ItemFormDto itemFormDto) {
        this.itemName = itemFormDto.getItemName();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량: " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;
    }

    // 상품의 재고 더하기
    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }
}
