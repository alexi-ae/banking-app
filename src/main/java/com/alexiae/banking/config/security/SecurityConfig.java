package com.alexiae.banking.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationProvider authenticationProvider;

  private static final String[] WHITE_LIST_URL = {
      "/api/v1/auth/login",
      "/api/v1/auth/register",
      "/authentication/**",
      "/ob/register"
  };

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(
            AbstractHttpConfigurer::disable) // Disabling CSRF as we use JWT which is immune to CSRF
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(WHITE_LIST_URL)
            .permitAll() // Whitelisting some paths from authentication
            .anyRequest().authenticated()) // All other requests must be authenticated
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session management
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class); // Registering our JwtAuthFilter
    return http.build();
  }
}
