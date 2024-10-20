package com.alexiae.banking.config.security;

import com.alexiae.banking.exception.ApiRestException;
import com.alexiae.banking.exception.ErrorReason;
import com.alexiae.banking.exception.ErrorResponse;
import com.alexiae.banking.exception.ErrorSource;
import com.alexiae.banking.model.enums.CustomerStatus;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
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
    try {
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
            if (userName != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {
              UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
              if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
              }
            }
          } else {
            writeErrorResponseApiRestException(response,
                ApiRestException.builder().reason(ErrorReason.UNAUTHORIZED)
                    .source(ErrorSource.BUSINESS_SERVICE).build());
            return;
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

    } catch (ExpiredJwtException e) {
      writeErrorResponseApiRestException(response,
          ApiRestException.builder().reason(ErrorReason.UNAUTHORIZED)
              .source(ErrorSource.BUSINESS_SERVICE).build());
    } catch (Exception e) {
      writeErrorResponseApiRestException(response,
          ApiRestException.builder().reason(ErrorReason.INTERNAL_SERVER_ERROR)
              .source(ErrorSource.UNKNOWN_SOURCE).build());
    }
  }

  public static void writeErrorResponseApiRestException(HttpServletResponse response,
      ApiRestException apiRestException) throws IOException {

    String jsonError = ErrorResponse.builder()
        .code(apiRestException.getReason().getErrorCode())
        .reason(apiRestException.getReason())
        .source(apiRestException.getSource())
        .errors(
            apiRestException.getMessage() == null
                ? Collections.emptyList()
                : Collections.singletonList(apiRestException.getMessage()))
        .build().toString();

    response.setStatus(apiRestException.getReason().getHttpStatus().value());
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(jsonError);
  }
}
