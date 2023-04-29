package com.yellow.test.config.security;

import com.yellow.test.config.security.additional.CustomTokenConverter;
import com.yellow.test.props.CustomSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private CustomSecurityProperties properties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();

        for (CustomSecurityProperties.Client client : properties.getClients()) {
            builder.withClient(client.getId())
                    .secret(passwordEncoder.encode(client.getSecret()))
                    .autoApprove(client.getAutoApprove())
                    .authorizedGrantTypes(client.getGrantTypes().toArray(new String[0]))
                    .scopes(client.getScopes().toArray(new String[0]))
                    .accessTokenValiditySeconds(client.getAccessTokenValiditySeconds())
                    .refreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
        }
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager).userDetailsService(userDetailsService);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                properties.getCertificate().getStore().getFile(),
                properties.getCertificate().getStore().getPassword().toCharArray()
        );
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(
                properties.getCertificate().getKey().getAlias(),
                properties.getCertificate().getKey().getPassword().toCharArray()
        );

        JwtAccessTokenConverter converter = new CustomTokenConverter();
        converter.setKeyPair(keyPair);
        return converter;
    }

}










