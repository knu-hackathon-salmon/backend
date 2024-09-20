package com.knu.salmon.api.domain.chat.controller;


import com.knu.salmon.api.domain.chat.service.ChatService;
import com.knu.salmon.api.domain.food.dto.request.CreateFoodDto;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.global.spec.response.ApiBasicResponse;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

   private final ChatService chatService;

    @PostMapping("/{foodId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse> createChat(
            @PathVariable("foodId") Long foodId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ApiDataResponse apiDataResponse = chatService.createChat(foodId, principalDetails);
        return ResponseEntity.status(apiDataResponse.getCode()).body(apiDataResponse);
    }
}
