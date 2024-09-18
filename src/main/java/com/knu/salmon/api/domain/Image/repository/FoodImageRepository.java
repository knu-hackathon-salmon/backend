package com.knu.salmon.api.domain.Image.repository;

import com.knu.salmon.api.domain.Image.entity.FoodImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodImageRepository extends JpaRepository<FoodImage, Long> {
}
