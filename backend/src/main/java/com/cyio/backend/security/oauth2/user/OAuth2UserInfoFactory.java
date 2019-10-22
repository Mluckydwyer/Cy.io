package com.cyio.backend.security.oauth2.user;

import com.cyio.backend.exception.OAuth2AuthenticationProcessingException;
import com.cyio.backend.model.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationID, Map<String, Object> attributes){
        if (registrationID.equalsIgnoreCase(AuthProvider.google.toString())){
            return new GoogleOAuth2UserInfo(attributes);
        }
        else {
            throw new OAuth2AuthenticationProcessingException("Login in with " + registrationID + "is not supported yet");

        }
    }
}
