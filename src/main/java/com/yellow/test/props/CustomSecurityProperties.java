package com.yellow.test.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "settings.security")
public class CustomSecurityProperties {

    @NotNull
    private Encoder encoder;

    @NotNull
    private List<Client> clients;

    @NotNull
    private Certificate certificate;

    @Getter
    @Setter
    public static class Encoder {

        @NotNull
        private Integer strength;
    }

    @Getter
    @Setter
    public static class Client {

        @NotBlank
        private String id;

        @NotBlank
        private String secret;

        @NotNull
        private Boolean autoApprove;

        @NotEmpty
        private List<String> grantTypes;

        @NotEmpty
        private List<String> scopes;

        @Min(1)
        private Integer accessTokenValiditySeconds;

        @Min(1)
        private Integer refreshTokenValiditySeconds;
    }

    @Getter
    @Setter
    public static class Certificate {

        @NotNull
        private Store store;

        @NotNull
        private Key key;

        @Getter
        @Setter
        public static class Store {

            @NotNull
            private Resource file;

            @NotBlank
            private String password;
        }

        @Getter
        @Setter
        public static class Key {

            @NotBlank
            private String alias;

            @NotBlank
            private String password;
        }
    }

}
