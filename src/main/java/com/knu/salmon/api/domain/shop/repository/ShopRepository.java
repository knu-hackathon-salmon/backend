package com.knu.salmon.api.domain.shop.repository;

import com.knu.salmon.api.domain.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    Shop findByMemberId(Long memberId);

}
