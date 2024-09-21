package com.knu.salmon.api.domain.message.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor // 기본 생성자 추가
@Builder
public class MessageDto {

    @JsonProperty("messageId") // JSON에서의 이름 명시
    private Long messageId;

    @JsonProperty("chatId")
    private Long chatId;

    @JsonProperty("senderEmail")
    private String senderEmail;

    @JsonProperty("senderName")
    private String senderName;

    @JsonProperty("content")
    private String content;

    @JsonProperty("createTime")
    private LocalDateTime createTime;

    // 기본 생성자와 Builder를 사용할 때 필요한 추가 생성자
    public MessageDto(Long messageId, Long chatId, String senderEmail, String senderName, String content, LocalDateTime createTime) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.senderEmail = senderEmail;
        this.senderName = senderName;
        this.content = content;
        this.createTime = createTime;
    }
}
