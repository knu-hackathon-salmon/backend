package com.knu.salmon.api.global.error.custom;

import com.knu.salmon.api.global.error.errorcode.custom.ChatErrorCode;
import com.knu.salmon.api.global.error.errorcode.custom.ShopErrorCode;

public class ChatException extends RuntimeException{

    private ChatErrorCode chatErrorCode;

    public ChatException(ChatErrorCode chatErrorCode) {
        this.chatErrorCode = chatErrorCode;
    }

    public ChatException(String message, ShopErrorCode shopErrorCode) {
        super(message);
        this.chatErrorCode = chatErrorCode;
    }
}