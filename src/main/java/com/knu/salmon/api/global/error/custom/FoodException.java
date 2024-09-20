package com.knu.salmon.api.global.error.custom;

import com.knu.salmon.api.global.error.errorcode.custom.FoodErrorCode;
import lombok.Getter;

@Getter
public class FoodException extends RuntimeException{

    private FoodErrorCode foodErrorCode;

    public FoodException(FoodErrorCode foodErrorCode) {
        this.foodErrorCode = foodErrorCode;
    }

    public FoodException(String message, FoodErrorCode foodErrorCode) {
        super(message);
        this.foodErrorCode = foodErrorCode;
    }
}