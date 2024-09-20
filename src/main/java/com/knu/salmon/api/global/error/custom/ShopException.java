package com.knu.salmon.api.global.error.custom;

import com.knu.salmon.api.global.error.errorcode.custom.FoodErrorCode;
import com.knu.salmon.api.global.error.errorcode.custom.ShopErrorCode;

public class ShopException extends RuntimeException{

    private ShopErrorCode shopErrorCode;

    public ShopException(ShopErrorCode shopErrorCode) {
        this.shopErrorCode = shopErrorCode;
    }

    public ShopException(String message, ShopErrorCode shopErrorCode) {
        super(message);
        this.shopErrorCode = shopErrorCode;
    }
}