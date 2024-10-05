package com.alexiae.banking.facede;

import com.alexiae.banking.model.api.JwtRequest;
import com.alexiae.banking.model.api.JwtResponse;
import com.alexiae.banking.config.security.JwtService;
import com.alexiae.banking.model.entity.Customer;
import com.alexiae.banking.service.CustomerService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthFacade {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private JwtService jwtTokenUtil;


  @SneakyThrows
  public JwtResponse auth(JwtRequest authenticationRequest) {
    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

    Customer customer = customerService.findByUsername(authenticationRequest.getUsername());
    String token = jwtTokenUtil.generateToken(customer.getUser());

    return new JwtResponse(token);
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException("INVALID_CREDENTIALS", e);
    }
  }
}
