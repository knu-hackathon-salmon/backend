package com.knu.salmon.api.global.error.custom;

import com.knu.salmon.api.global.error.errorcode.JwtTokenErrorCode;
import lombok.Getter;

@Getter
public class JwtTokenException extends RuntimeException{

    private JwtTokenErrorCode jwtTokenErrorCode;

    public JwtTokenException(JwtTokenErrorCode jwtTokenErrorCode) {
        this.jwtTokenErrorCode = jwtTokenErrorCode;
    }
}
