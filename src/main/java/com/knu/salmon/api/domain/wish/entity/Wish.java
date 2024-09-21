package com.knu.salmon.api.domain.wish.entity;

import com.knu.salmon.api.domain.customer.entity.Customer;
import com.knu.salmon.api.domain.food.entity.Food;
import com.knu.salmon.api.domain.shop.entity.Shop;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "wishes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Wish {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    @Builder
    public Wish(Customer customer, Food food) {
        this.customer = customer;
        this.food = food;
    }
}
