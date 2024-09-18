package com.knu.salmon.api.domain.food.repository;

import com.knu.salmon.api.domain.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long>{

}
