package com.yellow.test.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "settings.swagger")
public class SwaggerProperties {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String basePackage;

    @NotBlank
    private String antPattern;

    @NotNull
    private ApiKey apiKey;

    @NotNull
    private Security security;

    @Getter
    @Setter
    public static class ApiKey {

        @NotBlank
        private String name;

        @NotBlank
        private String keyname;

        @NotBlank
        private String passAs;
    }

    @Getter
    @Setter
    public static class Security {

        @NotBlank
        private String scopeSeparator;

        @NotNull
        private AuthorizationScope authorizationScope;

        @NotBlank
        private String reference;

        @Getter
        @Setter
        public static class AuthorizationScope {

            @NotBlank
            private String scope;

            @NotBlank
            private String description;
        }

    }
}
