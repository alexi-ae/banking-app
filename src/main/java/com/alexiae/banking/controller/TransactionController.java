package com.alexiae.banking.controller;


import com.alexiae.banking.facede.TransactionFacade;
import com.alexiae.banking.model.api.TransactionRequest;
import com.alexiae.banking.model.api.TransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {

  @Autowired
  private TransactionFacade transactionFacade;

  @PostMapping("/send")
  public TransactionResponse send(@RequestBody TransactionRequest request,
      @RequestAttribute("customerId") String customerId) {
    return transactionFacade.send(Long.parseLong(customerId), request);
  }

}
