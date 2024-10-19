package com.alexiae.banking.facede;

import com.alexiae.banking.exception.ApiRestException;
import com.alexiae.banking.exception.ErrorReason;
import com.alexiae.banking.exception.ErrorSource;
import com.alexiae.banking.model.api.TransactionMovementInfoResponse;
import com.alexiae.banking.model.api.TransactionRequest;
import com.alexiae.banking.model.api.TransactionResponse;
import com.alexiae.banking.model.entity.Account;
import com.alexiae.banking.model.entity.Customer;
import com.alexiae.banking.model.entity.Transaction;
import com.alexiae.banking.model.entity.TransactionMovement;
import com.alexiae.banking.model.enums.AccountStatus;
import com.alexiae.banking.model.enums.MovementType;
import com.alexiae.banking.model.enums.TransactionStatus;
import com.alexiae.banking.service.AccountService;
import com.alexiae.banking.service.CustomerService;
import com.alexiae.banking.service.TransactionMovementService;
import com.alexiae.banking.service.TransactionService;
import com.alexiae.banking.utils.FormatUtils;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionFacade {

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private TransactionMovementService transactionMovementService;

  public TransactionResponse send(Long customerId, TransactionRequest request) {
    Customer customer = customerService.listById(customerId);
    Optional<Account> accountOptional = customer.getAccounts().stream()
        .filter(account -> account.getNumber().equals(request.getOriginAccount())).findFirst();
    if (accountOptional.isEmpty()) {
      throw new RuntimeException("Fraud by transaction origin account");
    }

    Account originAccount = accountService.getByAccountNumber(request.getOriginAccount());
    Account destinationAccount = accountService.getByAccountNumber(request.getDestinationAccount());
    validateProcess(request, originAccount, destinationAccount);

    Transaction transaction = new Transaction();
    transaction.setAmount(request.getAmount());
    transaction.setDescription(request.getDescription());
    transaction.setStatus(TransactionStatus.PENDING);
    transaction = transactionService.create(transaction);

    originAccount.setCurrentBalance(
        originAccount.getCurrentBalance().subtract(request.getAmount()));
    destinationAccount.setCurrentBalance(
        destinationAccount.getCurrentBalance().add(request.getAmount()));
    accountService.update(originAccount);
    accountService.update(destinationAccount);

    TransactionMovement trxMovementOrigin = new TransactionMovement();
    trxMovementOrigin.setTransaction(transaction);
    trxMovementOrigin.setAccount(originAccount);
    trxMovementOrigin.setAmount(transaction.getAmount().negate());
    trxMovementOrigin.setMovementType(MovementType.SENT);
    transactionMovementService.create(trxMovementOrigin);

    TransactionMovement trxMovementDestiny = new TransactionMovement();
    trxMovementDestiny.setTransaction(transaction);
    trxMovementDestiny.setAccount(destinationAccount);
    trxMovementDestiny.setAmount(transaction.getAmount());
    trxMovementDestiny.setMovementType(MovementType.RECEIVED);
    transactionMovementService.create(trxMovementDestiny);
    transaction.setStatus(TransactionStatus.SUCCESS);
    transactionService.create(transaction);

    String operationCode = FormatUtils.generateOperationCode(10, transaction.getId());
    //String operationCode = String.format("%-15d", transaction.getId()).replace(' ', '0');
    return TransactionResponse.builder().originAccount(request.getOriginAccount())
        .destinationAccount(request.getDestinationAccount()).description(request.getDescription())
        .amount(request.getAmount()).operationCode(operationCode).build();
  }

  private void validateProcess(TransactionRequest request, Account originAccount,
      Account destinationAccount) {
    if (!AccountStatus.ACTIVE.equals(originAccount.getStatus())) {
      throw new RuntimeException("Origin Account not is active");
    }

    if (!AccountStatus.ACTIVE.equals(destinationAccount.getStatus())) {
      throw new RuntimeException("Origin Account not is active");
    }

    if (originAccount.getCurrentBalance().compareTo(request.getAmount()) < 0) {
      throw new RuntimeException("insufficient balance");
    }

    if (!originAccount.getCurrency().equals(destinationAccount.getCurrency())) {
      throw new RuntimeException("todavia no se aplica tipo de cambio");
    }

  }

  public List<TransactionMovementInfoResponse> getMovementsByAccountId(Long accountId,
      long customerId) {
    boolean customerHasAccount = accountService.existsByIdAndCustomerId(accountId, customerId);
    if (Boolean.FALSE.equals(customerHasAccount)) {
      throw ApiRestException.builder().source(ErrorSource.BUSINESS_SERVICE)
          .reason(ErrorReason.BAD_REQUEST).build();
    }

    return transactionMovementService.getByAccountId(accountId).stream().map(
        transactionMovement -> TransactionMovementInfoResponse.builder()
            .operationCode(
                FormatUtils.generateOperationCode(10, transactionMovement.getTransaction().getId())
                    + "-" + FormatUtils.generateOperationCode(10,
                    transactionMovement.getId()))
            .amount(transactionMovement.getAmount())
            .description(transactionMovement.getTransaction().getDescription())
            .status(transactionMovement.getTransaction().getStatus())
            .type(transactionMovement.getMovementType())
            .creationDate(transactionMovement.getCreationDate()).build()).toList();
  }
}
