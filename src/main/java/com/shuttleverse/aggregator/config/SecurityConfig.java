package com.shuttleverse.aggregator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for setting up OAuth2 resource server with JWT authentication. Configures
 * the application to validate JWT tokens using the public JWK set from Google's OAuth2 service.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * Configures the security settings for the application. This includes disabling CSRF, requiring
   * authentication for all requests, and setting up OAuth2 resource server with JWT support using
   * Google's public JWK Set.
   *
   * @param http The HttpSecurity object used for configuring security.
   * @return The SecurityFilterChain that defines the security configuration.
   * @throws Exception If an error occurs during the configuration of security.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorize -> authorize
            .anyRequest().authenticated()
        )
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
            )
        );
    return http.build();
  }

}