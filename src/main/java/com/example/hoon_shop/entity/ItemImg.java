package com.example.hoon_shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "item_img")
@Getter
@Setter
public class ItemImg extends BaseEntity {

    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgName; // 이미지 파일명

    private String orgImgName;  // 원본 이미지 파일명

    private String imgUrl;

    private String repImgYn;    // 대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String orgImgName, String imgName, String imgUrl) {
        this.orgImgName = orgImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
