package com.knu.salmon.api.domain.food.dto.response;

import com.knu.salmon.api.domain.Image.entity.FoodImage;
import com.knu.salmon.api.domain.food.entity.Food;
import com.knu.salmon.api.domain.food.entity.FoodCategory;
import com.knu.salmon.api.domain.shop.entity.Shop;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class FoodDetailResponseDto {
    private Long foodId;
    private String shopImageUrl;
    private String shopName;
    private String title;
    private int price;
    private int likesCount;
    private String roadAddress;
    private String businessHours;
    private String phoneNumber;
    private int stock;
    private String expiration;
    private String content;
    private List<String> foodImages;
    private Boolean trading;

    @Builder
    public FoodDetailResponseDto(Long foodId, String shopImageUrl, String shopName, String title, int price, int likesCount, String roadAddress, String businessHours, String phoneNumber, int stock, String expiration, String content, List<String> foodImages, Boolean trading) {
        this.foodId = foodId;
        this.shopImageUrl = shopImageUrl;
        this.shopName = shopName;
        this.title = title;
        this.price = price;
        this.likesCount = likesCount;
        this.roadAddress = roadAddress;
        this.businessHours = businessHours;
        this.phoneNumber = phoneNumber;
        this.stock = stock;
        this.expiration = expiration;
        this.content = content;
        this.foodImages = foodImages;
        this.trading = trading;
    }

    public static FoodDetailResponseDto fromFood(Food food, Shop shop){
        return FoodDetailResponseDto.builder()
                .foodId(food.getId())
                .shopImageUrl(shop.getPhotoUrl())
                .shopName(shop.getShopName())
                .title(food.getTitle())
                .price(food.getPrice())
                .likesCount(food.getLikesCount())
                .roadAddress(shop.getRoadAddress())
                .businessHours(shop.getBusinessHours())
                .phoneNumber(shop.getPhoneNumber())
                .stock(food.getStock())
                .expiration(food.getExpiration())
                .content(food.getContent())
                .foodImages(food.getImages().stream().map(FoodImage::getImageUrl).toList())
                .trading(food.getTrading())
                .build();

    }
}

