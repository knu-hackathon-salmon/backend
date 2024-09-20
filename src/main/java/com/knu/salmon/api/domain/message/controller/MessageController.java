package com.knu.salmon.api.domain.message.controller;

import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.message.service.MessageService;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;
    @PostMapping("/{chatId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse> getMessages(
            @PathVariable("chatId") Long chatId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ApiDataResponse apiDataResponse = messageService.getMessages(chatId, principalDetails);
        return ResponseEntity.status(apiDataResponse.getCode()).body(apiDataResponse);
    }
}
