package com.knu.salmon.api.auth.oauth2.dto.response;

public interface Oauth2Response {
    String getProvider();

    String getProviderId();

    String getEmail();

    String getName();

}
