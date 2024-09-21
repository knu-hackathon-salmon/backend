package com.knu.salmon.api.domain.wish.controller;

import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.wish.dto.response.MyFoodWishResponseDto;
import com.knu.salmon.api.domain.wish.service.WishService;
import com.knu.salmon.api.global.spec.response.ApiBasicResponse;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wish")
public class ApiWishController implements SwaggerWishApi{
    private final WishService wishService;

    @PostMapping("{foodId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiBasicResponse> createFoodWish(
            @PathVariable("foodId") Long foodId,
            @AuthenticationPrincipal PrincipalDetails principalDetails){
        ApiBasicResponse response = wishService.createFoodWish(principalDetails, foodId);
        return ResponseEntity.status(response.getCode()).body(response);
    }


    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<MyFoodWishResponseDto>>> getFoodWishList(
            @AuthenticationPrincipal PrincipalDetails principalDetails){
        ApiDataResponse<List<MyFoodWishResponseDto>> wishList = wishService.getWishList(principalDetails);
        return ResponseEntity.status(wishList.getCode()).body(wishList);
    }
}
