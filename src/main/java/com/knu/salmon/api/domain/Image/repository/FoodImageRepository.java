package com.knu.salmon.api.domain.Image.repository;

import com.knu.salmon.api.domain.Image.entity.FoodImage;
import com.knu.salmon.api.domain.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodImageRepository extends JpaRepository<FoodImage, Long> {

    List<FoodImage> findByFood(Optional<Food> food);

    List<FoodImage> findAllByFoodId(Long foodId);

    void deleteAllByFoodId(Long foodId);
}
