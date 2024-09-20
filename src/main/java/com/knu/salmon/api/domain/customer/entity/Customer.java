package com.knu.salmon.api.domain.customer.entity;

import com.knu.salmon.api.domain.chat.entity.Chat;
import com.knu.salmon.api.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "customers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    private String nickname;

    private String photoUrl;

    private double latitude;

    private double longitude;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>();

    @Builder
    public Customer(String nickname, String photoUrl, double latitude, double longitude, Member member) {
        this.nickname = nickname;
        this.photoUrl = photoUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.member = member;
    }
}
