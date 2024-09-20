package com.knu.salmon.api.global.error.errorcode.custom;


import com.knu.salmon.api.global.error.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChatErrorCode implements ErrorCode {

    NO_EXIST_CHAT_EXCEPTION(HttpStatus.NOT_FOUND, "요청 id에 해당하는 Chat가 없습니다");

    ChatErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String message;
}
