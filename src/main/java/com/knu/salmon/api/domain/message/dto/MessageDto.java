package com.knu.salmon.api.domain.message.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class MessageDto {

    private Long messageId;

    private Long chatId;

    private String senderEmail;

    private String senderName;

    private String content;

    private LocalDateTime createTime;
}
