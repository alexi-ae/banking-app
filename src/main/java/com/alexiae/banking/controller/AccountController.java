package com.alexiae.banking.controller;

import com.alexiae.banking.facede.AccountFacade;
import com.alexiae.banking.model.api.AccountResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

  @Autowired
  private AccountFacade accountFacade;

  @PostMapping
  public List<AccountResponse> accounts(@RequestAttribute("email") String email) {

    return accountFacade.getAccounts(email);
  }


}
