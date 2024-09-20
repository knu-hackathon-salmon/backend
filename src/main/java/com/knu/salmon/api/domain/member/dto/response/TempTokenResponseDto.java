package com.knu.salmon.api.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TempTokenResponseDto {
    private String accessToken;
    private String refreshToken;

    @Builder
    public TempTokenResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
