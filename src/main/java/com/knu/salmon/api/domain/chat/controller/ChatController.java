package com.knu.salmon.api.domain.chat.controller;


import com.knu.salmon.api.domain.chat.dto.ChatDto;
import com.knu.salmon.api.domain.chat.service.ChatService;
import com.knu.salmon.api.domain.food.dto.request.CreateFoodDto;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.message.dto.MessageDto;
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

    @PostMapping("/{foodId}/customer/{customerId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<MessageDto>>> createChat(
            @PathVariable("foodId") Long foodId,
            @PathVariable("customerId") Long customerId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ApiDataResponse<List<MessageDto>> apiDataResponse = chatService.createChat(foodId, customerId, principalDetails);
        return ResponseEntity.status(apiDataResponse.getCode()).body(apiDataResponse);
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<ChatDto>>> getAllChatsByMember(
            @AuthenticationPrincipal PrincipalDetails principalDetails){

        ApiDataResponse<List<ChatDto>> allChatsByMember = chatService.getAllChatsByMember(principalDetails);

        return ResponseEntity.status(allChatsByMember.getCode()).body(allChatsByMember);

    }

   
}
