package com.knu.salmon.api.domain.chat.repository;

import com.knu.salmon.api.domain.chat.entity.Chat;
import com.knu.salmon.api.domain.customer.entity.Customer;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.seller.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByShopAndCustomer(Shop shop, Customer customer);

    List<Chat> findByMember(Member member);

}
