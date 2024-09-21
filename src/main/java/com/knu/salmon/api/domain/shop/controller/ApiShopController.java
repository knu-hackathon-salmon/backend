package com.knu.salmon.api.domain.shop.controller;

import com.knu.salmon.api.domain.food.dto.request.CreateFoodDto;
import com.knu.salmon.api.domain.food.dto.response.FoodDetailResponseDto;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.shop.dto.MyFoodsResponseDto;
import com.knu.salmon.api.domain.shop.repository.ShopRepository;
import com.knu.salmon.api.domain.shop.service.ShopService;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop")
public class ApiShopController implements SwaggerShopApi {
    private final ShopService shopService;
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<MyFoodsResponseDto>>> getMyFoods(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ApiDataResponse<List<MyFoodsResponseDto>> response = shopService.getMyFoods(principalDetails);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
