package com.yellow.test.config.security.additional;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.security.Principal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUuidExtractor {

    public static String extract(OAuth2Authentication authentication) {
        CustomAuthenticationToken token = (CustomAuthenticationToken) authentication.getUserAuthentication();
        return token.getUuid();
    }

    public static String extract(Principal principal) {
        OAuth2Authentication authentication = (OAuth2Authentication) principal;
        return extract(authentication);
    }
}
