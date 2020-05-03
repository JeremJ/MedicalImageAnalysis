package com.imageanalysis.config;

import lombok.experimental.UtilityClass;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;

@UtilityClass
public class SecurityUtil {

    public static AccessToken getCurrentAuthenticateToken(Authentication authentication) {
        return ((SimpleKeycloakAccount) authentication.getDetails()).getKeycloakSecurityContext().getToken();
    }
}
