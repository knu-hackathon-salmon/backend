package com.knu.salmon.api.domain.chat.entity;

import com.knu.salmon.api.domain.customer.entity.Customer;
import com.knu.salmon.api.domain.food.entity.Food;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.message.entity.Message;
import com.knu.salmon.api.domain.seller.entity.Shop;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "chats")
public class Chat
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_Id", updatable = false)
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> message = new ArrayList<>();


    @Builder
    public Chat( Shop shop, Customer customer, Food food) {
        this.shop = shop;
        this.customer = customer;
        this.food = food;
    }

}
