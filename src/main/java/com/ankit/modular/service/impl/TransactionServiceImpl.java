package com.ankit.modular.service.impl;

import com.ankit.modular.Utils.Constants;
import com.ankit.modular.controller.request.TransactionRequest;
import com.ankit.modular.controller.response.TransactionResponse;
import com.ankit.modular.entity.Account;
import com.ankit.modular.entity.Balance;
import com.ankit.modular.entity.Transaction;
import com.ankit.modular.enumuration.TransactionType;
import com.ankit.modular.exceptions.AccountNotFound;
import com.ankit.modular.exceptions.DescriptionMissing;
import com.ankit.modular.exceptions.InsufficientFund;
import com.ankit.modular.exceptions.InvalidAmount;
import com.ankit.modular.publisher.MessagePublisher;
import com.ankit.modular.repository.AccountRepository;
import com.ankit.modular.repository.TransactionRepository;
import com.ankit.modular.service.TransactionService;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionrequest) {
        if(BigDecimal.ZERO.compareTo(transactionrequest.getAmmount()) > 0)
            throw new InvalidAmount(Constants.INVALID_AMMOUNT);

        if(transactionrequest.getDescription()==null || transactionrequest.getDescription().isEmpty())
            throw new DescriptionMissing(Constants.DISCRIPTION_MISSING);

        Optional<Account> accountOptional = accountRepository.findById(transactionrequest.getAccountId());
        if (!accountOptional.isPresent()){
            log.debug("Account not found for "+ transactionrequest.getAccountId());
            throw new AccountNotFound(Constants.ACCOUNT_NOT_FOUND);
        }

        Account account = accountOptional.get();
        Optional<Balance> balanceOptional = account.getBalanceList().stream().filter(blnc -> blnc.getType().equals(transactionrequest.getCurrency())).findFirst();

        if (!balanceOptional.isPresent()) {
            log.debug("Invalid currency");
            throw new AccountNotFound(Constants.INVALID_CURRENCY);
        }

        Balance balance = balanceOptional.get();
        if (TransactionType.OUT.equals(transactionrequest.getDirection())) {
            BigDecimal balanceAmmount = balance.getAmmount().subtract(transactionrequest.getAmmount());
            if (balanceAmmount.compareTo(BigDecimal.ZERO) < 0) {
                log.debug("Insufficient fund "+transactionrequest.getAmmount()+" for "+transactionrequest.getAccountId());
                throw new InsufficientFund(Constants.INSUFFICIENT_FUNDS);
            }
            balance.setAmmount(balanceAmmount);
        } else {
            balance.setAmmount(balance.getAmmount().add(transactionrequest.getAmmount()));
        }

        Transaction transaction = Transaction.builder()
                .account(account)
                .ammount(transactionrequest.getAmmount())
                .currency(transactionrequest.getCurrency())
                .direction((transactionrequest.getDirection()))
                .description(transactionrequest.getDescription())
                .build();

        log.debug("Transaction"+ transactionrequest.getDirection().toString()+" of "+transactionrequest.getAmmount()+ " from "+transactionrequest.getAccountId());

        TransactionResponse transactionResponse = convertToResponse(transactionRepository.save(transaction));

        // Publish Account Message to queue for other consumer.
        //publisher.publishMessage(transactionResponse);

        return transactionResponse;

    }

    private TransactionResponse convertToResponse(Transaction transaction) {

        return TransactionResponse.builder()
                .accountId(transaction.getAccount().getId())
                .transactionId(transaction.getId())
                .balance(transaction.getAccount().getBalanceList().stream()
                        .filter(blnc -> blnc.getType().equals(transaction.getCurrency()))
                        .findFirst().orElseGet(Balance::new).getAmmount())
                .amount(transaction.getAmmount())
                .currency(transaction.getCurrency())
                .description(transaction.getDescription())
                .direction(transaction.getDirection())
                .build();
    }

    @Override
    public List<TransactionResponse> getTransaction(int accountId) {
        /*Optional<Account> accountOptional = accountRepository.findById(accountId);
        if(!accountOptional.isPresent()) {
            log.debug("Account not found for "+ accountId);
            throw new AccountNotFound(Constants.ACCOUNT_NOT_FOUND);
        }*/

        log.debug("Return account details for "+ accountId);
        return transactionRepository.findByAccountId(accountId).stream().map(this::convertToResponse).collect(Collectors.toList());
    }
}
