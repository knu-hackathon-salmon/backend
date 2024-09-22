package com.knu.salmon.api.domain.chat.dto;


import com.knu.salmon.api.domain.food.entity.Food;
import com.knu.salmon.api.domain.shop.dto.MyFoodsResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatDto {

    private Long chatId;
    private Long foodId;
    private String foodTitle;
    private String foodName;
    private String opponentName;
    private String imageUrl;


    @Builder
    public ChatDto(Long chatId, Long foodId, String foodTitle, String foodName, String opponentName, String imageUrl){

        this.chatId = chatId;
        this.foodId = foodId;
        this.imageUrl = imageUrl;
        this.foodTitle = foodTitle;
        this.foodName = foodName;
        this.opponentName = opponentName;
    }

}
