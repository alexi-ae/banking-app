package com.alexiae.banking.config.security;

import com.alexiae.banking.exception.ApiRestException;
import com.alexiae.banking.exception.ErrorReason;
import com.alexiae.banking.exception.ErrorSource;
import com.alexiae.banking.model.enums.CustomerStatus;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  private final List<String> ONB_LIST_URL = Arrays.asList("/ob");

  private boolean isAllowedByObnUri(String uri) {
    return this.ONB_LIST_URL.stream().anyMatch(uri::startsWith);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      String userName = jwtService.getUsernameFromToken(token);
      request.setAttribute("email", userName);
      String state = jwtService.getCustomerState(token);
      String customerId = jwtService.getCustomerId(token);
      request.setAttribute("customerId", customerId);

      if (CustomerStatus.PENDING.name().equals(state)) {
        if (isAllowedByObnUri(request.getRequestURI())) {
          if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if (jwtService.validateToken(token, userDetails)) {
              UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
              authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(authToken);
            }
          }
        } else {
          throw ApiRestException.builder()
              .reason(ErrorReason.UNAUTHORIZED)
              .source(ErrorSource.REST_CONTROLLER)
              .build();
        }
      }

      if (CustomerStatus.APPROVED.name().equals(state)) {
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
          if (jwtService.validateToken(token, userDetails)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
          }
        }
      }
    }
    filterChain.doFilter(request, response);
  }

}
