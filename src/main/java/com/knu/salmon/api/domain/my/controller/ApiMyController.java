package com.knu.salmon.api.domain.my.controller;

import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.shop.dto.MyFoodsResponseDto;
import com.knu.salmon.api.domain.shop.service.ShopService;
import com.knu.salmon.api.domain.wish.dto.response.MyFoodWishResponseDto;
import com.knu.salmon.api.domain.wish.service.WishService;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my")
public class ApiMyController {
    private final WishService wishService;
    private final ShopService shopService;

    @GetMapping("/wish-list")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<MyFoodWishResponseDto>>> getFoodWishList(
            @AuthenticationPrincipal PrincipalDetails principalDetails){
        ApiDataResponse<List<MyFoodWishResponseDto>> wishList = wishService.getWishList(principalDetails);
        return ResponseEntity.status(wishList.getCode()).body(wishList);
    }


    @GetMapping("/trading-list")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<MyFoodsResponseDto>>> getMyFoods(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ApiDataResponse<List<MyFoodsResponseDto>> response = shopService.getMyFoods(principalDetails);
        return ResponseEntity.status(response.getCode()).body(response);
    }

}
