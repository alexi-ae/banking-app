package com.alexiae.banking.controller;

import com.alexiae.banking.facede.CustomerFacade;
import com.alexiae.banking.model.api.CustomerInfoResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {

  @Autowired
  private CustomerFacade customerFacade;

  @PostMapping("/info")
  public CustomerInfoResponse customerIndo(@RequestAttribute("email") String email) {

    return customerFacade.customerInfo(email);
  }


}
