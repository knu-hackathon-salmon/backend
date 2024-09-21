package com.knu.salmon.api.domain.wish.repository;

import com.knu.salmon.api.domain.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findAllByCustomerId(Long customerId);

    Boolean existsByCustomerIdAndFoodId(Long customerId, Long foodId);

    Wish findByCustomerIdAndFoodId(Long customerId, Long foodId);
}
