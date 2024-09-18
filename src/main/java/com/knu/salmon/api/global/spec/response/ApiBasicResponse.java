package com.knu.salmon.api.global.spec.response;

import lombok.Builder;
import lombok.Data;

@Data
public class ApiBasicResponse {
    private boolean status;
    private int code;
    private String message;

    @Builder
    public ApiBasicResponse(boolean status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
