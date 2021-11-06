package com.example.hoon_shop.entity;

import com.example.hoon_shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL // 영속성 옵션 설정
            , orphanRemoval = true, fetch = FetchType.LAZY) // 고아 객체 제거
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
