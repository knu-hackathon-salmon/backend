package com.knu.salmon.api.domain.food.repository;

import com.knu.salmon.api.domain.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long>{

    @Query("SELECT f FROM foods f JOIN FETCH f.shop")
    List<Food> findAllWithShop();

    @Query("SELECT f FROM foods f JOIN f.shop s WHERE " +
            "s.latitude BETWEEN :swLat AND :neLat AND " +
            "s.longitude BETWEEN :swLng AND :neLng " +
            "ORDER BY (6371 * acos(cos(radians(:userLat)) * cos(radians(s.latitude)) * cos(radians(s.longitude) - radians(:userLon)) + sin(radians(:userLat)) * sin(radians(s.latitude)))) ASC")
    List<Food> findFoodsInBoundsSortedByDistance(@Param("neLat") double neLat,
                                                 @Param("neLng") double neLng,
                                                 @Param("swLat") double swLat,
                                                 @Param("swLng") double swLng,
                                                 @Param("userLat") double userLat,
                                                 @Param("userLon") double userLon);

    @Query("SELECT f FROM foods f JOIN f.shop s WHERE " +
            "s.latitude BETWEEN :swLat AND :neLat AND " +
            "s.longitude BETWEEN :swLng AND :neLng")
    List<Food> findFoodsInArea(@Param("neLat") double neLat,
                               @Param("neLng") double neLng,
                               @Param("swLat") double swLat,
                               @Param("swLng") double swLng);

}
