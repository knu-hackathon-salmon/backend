package com.knu.salmon.api.global.error.custom;

import com.knu.salmon.api.global.error.errorcode.MemberErrorCode;
import lombok.Getter;

@Getter
public class MemberException extends RuntimeException{

    private MemberErrorCode memberErrorCode;

    MemberException(MemberErrorCode memberErrorCode) {
        this.memberErrorCode = memberErrorCode;
    }
}
