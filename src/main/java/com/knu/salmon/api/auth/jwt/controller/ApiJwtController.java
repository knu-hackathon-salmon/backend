package com.knu.salmon.api.auth.jwt.controller;

import com.knu.salmon.api.auth.jwt.service.JwtService;
import com.knu.salmon.api.global.spec.response.ApiBasicResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
@Slf4j
public class ApiJwtController {

    private final JwtService jwtService;

    /**
     * 리프레시로 액세스 토큰 받아오기
     */
    @GetMapping("/reissue")
    public ResponseEntity<ApiBasicResponse> getAccessToken(
            @CookieValue(value = "Authorization-refresh") String refreshToken){

        return jwtService.reissue(refreshToken);
    }
}
