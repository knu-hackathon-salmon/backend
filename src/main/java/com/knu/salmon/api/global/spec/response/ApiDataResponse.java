package com.knu.salmon.api.global.spec.response;

import lombok.Builder;
import lombok.Data;

@Data
public class ApiDataResponse<T> {

    private boolean status;
    private int code;
    private String message;
    private T data;

    @Builder
    public ApiDataResponse(boolean status, int code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
