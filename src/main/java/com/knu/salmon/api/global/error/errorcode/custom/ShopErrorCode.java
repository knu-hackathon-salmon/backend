package com.knu.salmon.api.global.error.errorcode.custom;


import com.knu.salmon.api.global.error.ErrorCodeProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ShopErrorCode implements ErrorCodeProvider {

    NO_EXIST_SHOP_EXCEPTION(HttpStatus.NOT_FOUND, "요청 id에 해당하는 Shop가 없습니다");

    ShopErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String message;
}
