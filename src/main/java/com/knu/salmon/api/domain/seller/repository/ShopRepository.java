package com.knu.salmon.api.domain.seller.repository;

import com.knu.salmon.api.domain.seller.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    Shop findByMemberId(Long memberId);
}
