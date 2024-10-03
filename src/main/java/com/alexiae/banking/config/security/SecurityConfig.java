package com.alexiae.banking.config.security;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// order 0
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private JwtAuthenticationEntryPoint authenticationEntryPoint;

  private JwtRequestFilter jwtRequestFilter;

  private UserDetailsService userDetailsService;

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults());
    http.csrf(csrf -> csrf.disable());
    http.sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(
        SessionCreationPolicy.STATELESS));
    http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(
        (request, response, exception) -> {
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
        }));
    http.authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
        .requestMatchers(HttpMethod.GET, "/**").permitAll()  // Permitir todas las peticiones GET
        .requestMatchers(HttpMethod.POST, "/**").permitAll()  // Permitir todas las peticiones POST
        .requestMatchers(HttpMethod.PUT, "/**").permitAll()  // Permitir todas las peticiones PUT
        .requestMatchers(HttpMethod.DELETE, "/**").permitAll()
        .requestMatchers("/actuator/health", "/actuator/metrics", "/actuator/metrics/**",
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/api/monitor/**",
            "/h2-console", "/h2-console/**", "/favicon.ico", "/authentication/**").permitAll()
        .requestMatchers("/").permitAll()
        .anyRequest().authenticated()
    );

    //http.addFilterBefore(jwtRequestFilter,
    //    UsernamePasswordAuthenticationFilter.class); // custom protocol Authorization
    return http.build();
  }


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }
}
