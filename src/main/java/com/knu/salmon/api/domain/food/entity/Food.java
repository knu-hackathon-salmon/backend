package com.knu.salmon.api.domain.food.entity;

import com.knu.salmon.api.domain.Image.entity.FoodImage;
import com.knu.salmon.api.domain.chat.entity.Chat;
import com.knu.salmon.api.domain.food.dto.request.UpdateFoodDto;
import com.knu.salmon.api.domain.shop.entity.Shop;
import com.knu.salmon.api.domain.wish.entity.Wish;
import com.knu.salmon.api.global.spec.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String title;

    private int stock;

    private String expiration;

    private int price;

    private String content;

    private double latitude;

    private double longitude;

    private int likesCount;

    @Enumerated(EnumType.STRING)
    private FoodCategory foodCategory;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<FoodImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private Boolean trading;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<Wish> wishes = new ArrayList<>();

    @Builder
    public Food(String title, int stock, String expiration, int price, String content, double latitude, double longitude, int likesCount, FoodCategory foodCategory, Shop shop, Boolean trading) {
        this.title = title;
        this.stock = stock;
        this.expiration = expiration;
        this.price = price;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.likesCount = likesCount;
        this.foodCategory = foodCategory;
        this.shop = shop;
        this.trading = trading;
    }




    public void updateFood(UpdateFoodDto updateFoodDto)
    {
        this.title = updateFoodDto.getNewTitle();
        this.stock = updateFoodDto.getNewStock();
        this.expiration = updateFoodDto.getNewExpiration();
        this.price = updateFoodDto.getNewPrice();
        this.content = updateFoodDto.getNewContent();
        this.foodCategory = updateFoodDto.getNewFoodCategory();
        this.trading = updateFoodDto.getNewTrading();
    }

    public void updateTrading(Boolean trading){
        this.trading = trading;
    }



}
