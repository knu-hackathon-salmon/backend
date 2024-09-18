package com.knu.salmon.api.domain.food.dto.response;

import com.knu.salmon.api.domain.food.entity.FoodCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class FoodDetailResponseDto {
    private Long foodId;

    private String title;

    private String name;

    private Long stock;

    private LocalDateTime expiration;

    private Long price;

    private String content;

    private FoodCategory foodCategory;

    private List<String> imageUrls;

    private String createdDate;

    @Builder
    public FoodDetailResponseDto(Long foodId, String title, String name, Long stock, LocalDateTime expiration, Long price, String content, FoodCategory foodCategory, List<String> imageUrls, String createdDate)
    {
        this.foodId = foodId;
        this.title = title;
        this.name = name;
        this.stock = stock;
        this.expiration = expiration;
        this.price = price;
        this.content = content;
        this.foodCategory = foodCategory;
        this.imageUrls = imageUrls;
    }
}

