package com.knu.salmon.api.domain.seller.repository;

import com.knu.salmon.api.domain.seller.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    Shop findByMemberId(Long memberId);

}
