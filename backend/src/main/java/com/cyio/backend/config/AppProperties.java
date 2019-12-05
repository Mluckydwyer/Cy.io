package com.cyio.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;


/**
 * Spring boot security configurations
 */

@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    /**
     * Auth class used to identify logged on users w/email and password
     */
    public static class Auth {
        private String tokenSecret; //string with the users information
        private long tokenExpirationMsec; //how long before the user has to log on again

        public String getTokenSecret() {
            return tokenSecret;
        }

        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        public long getTokenExpirationMsec() {
            return tokenExpirationMsec;
        }

        public void setTokenExpirationMsec(long tokenExpirationMsec) {
            this.tokenExpirationMsec = tokenExpirationMsec;
        }
    }

    /**
     * OAuth2 class  used to guide logged on users w oauth 2 signins
     */
    public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>(); //list of authorized uris

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }

    public Auth getAuth() {
        return auth;
    }

    public OAuth2 getOauth2() {
        return oauth2;
    }

}
