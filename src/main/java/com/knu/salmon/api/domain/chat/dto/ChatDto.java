package com.knu.salmon.api.domain.chat.dto;


import lombok.Builder;

@Builder
public class ChatDto {

    private Long chatId;
    private Long foodId;
    private String foodTitle;
    private String foodName;
    private String opponentName;
    private String imageUrl;
}
