package com.knu.salmon.api.global.error;

import org.springframework.http.HttpStatus;

public interface ErrorCodeProvider {
    HttpStatus getHttpStatus();
    String getMessage();
}
