package com.knu.salmon.api.domain.food.entity;

import com.knu.salmon.api.domain.Image.entity.FoodImage;
import com.knu.salmon.api.domain.food.dto.request.UpdateFoodDto;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.seller.entity.Shop;
import com.knu.salmon.api.global.spec.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.sql.Update;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "foods")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Food extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @Column(name = "food_title")
    private String title;

    @Column(name = "food_name")
    private String name;

    @Column(name = "food_stock")
    private Long stock;

    @Column(name = "food_expiration")
    private LocalDateTime expiration;

    @Column(name = "food_price")
    private Long price;

    @Column(name = "food_content")
    private String content;

    private double latitude;

    private double longitude;

    @Column(name = "food_category")
    @Enumerated(EnumType.STRING)
    private FoodCategory foodCategory;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<FoodImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(name = "trading")
    private Boolean trading;

    @Builder
    public Food(String title, String name, Long stock, LocalDateTime expiration, Long price, String content, double latitude, double longitude, FoodCategory foodCategory,  Shop shop, Boolean trading) {
        this.title = title;
        this.name = name;
        this.stock = stock;
        this.expiration = expiration;
        this.price = price;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.foodCategory = foodCategory;
        this.shop = shop;
        this.trading = trading;
    }



    public void updateFood(UpdateFoodDto updateFoodDto)
    {
        this.title = updateFoodDto.getNewTitle();
        this.name = updateFoodDto.getNewName();
        this.stock = updateFoodDto.getNewStock();
        this.expiration = updateFoodDto.getNewExpiration();
        this.price = updateFoodDto.getNewPrice();
        this.content = updateFoodDto.getNewContent();
        this.foodCategory = updateFoodDto.getNewFoodCategory();
        this.trading = updateFoodDto.getNewTrading();
    }



}
