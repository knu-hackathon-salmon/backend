package com.knu.salmon.api.domain.member.entity;

public enum TokenStatus {
    COOKIE_NULL,
    INVALID,
    ACCESS_TOKEN_EXPIRED,
    REFRESH_TOKEN_EXPIRED,
    REFRESH_TOKEN_MISMATCH,
    UNSUPPORTED,
    MISMATCH_CLAIMS
}

