package com.knu.salmon.api.domain.seller.entity;

import com.knu.salmon.api.domain.chat.entity.Chat;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.global.spec.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "shops")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Shop extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    private String shopName;

    private String shopDescription;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double latitude;

    private double longitude;

    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>();

    @Builder
    public Shop(String shopName, String shopDescription, LocalDateTime startTime, LocalDateTime endTime, double latitude, double longitude, String phoneNumber, Member member) {
        this.shopName = shopName;
        this.shopDescription = shopDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.member = member;
    }
}
