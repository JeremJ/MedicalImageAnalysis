package com.imageanalysis.keycloak;

import lombok.Data;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static java.util.Arrays.asList;

@Configuration
@Data
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakConfig {

    private String authServerUrl;
    private String realm;
    private String resource;
    private Credentials credentials;

    @Bean
    org.keycloak.admin.client.Keycloak getKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .username("technicaluser")
                .password("technicaluser")
                .clientId(resource).clientSecret(credentials.getSecret())
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
    }

    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(asList("*"));
        configuration.setAllowedMethods(asList("GET", "POST", "DELETE", "OPTIONS", "PUT", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(asList("*"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
