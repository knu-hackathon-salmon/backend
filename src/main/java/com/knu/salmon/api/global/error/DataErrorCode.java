package com.knu.salmon.api.global.error;

public interface DataErrorCode<T> extends ErrorCodeProvider {
    T getData();
}
