package com.knu.salmon.api.domain.message.entity;


import com.knu.salmon.api.domain.chat.entity.Chat;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.global.spec.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "messages")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Column(name = "content")
    private String content;

    @Builder
    public Message(Long id, Member sender, Chat chat, String content) {
        this.id = id;
        this.sender = sender;
        this.chat = chat;
        this.content = content;
    }
}
