package com.ankit.modular.service.impl;

import com.ankit.modular.Utils.Constants;
import com.ankit.modular.controller.request.AccountRequest;
import com.ankit.modular.controller.response.AccountResponse;
import com.ankit.modular.controller.response.BalanceResponse;
import com.ankit.modular.entity.Account;
import com.ankit.modular.entity.Balance;
import com.ankit.modular.enumuration.Currency;
import com.ankit.modular.exceptions.AccountNotFound;
import com.ankit.modular.publisher.MessagePublisher;
import com.ankit.modular.repository.AccountRepository;
import com.ankit.modular.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessagePublisher publisher;

    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) {

        Account account = Account.builder()
                .customerId(accountRequest.getCustomerId())
                .country(accountRequest.getCountry())
                .balanceList(convertBalances(accountRequest.getCurrencies()))
                .build();
        AccountResponse accountResponse = convertToResponse(accountRepository.save(account));

        log.debug("Account created for "+accountResponse.getCustomerId()+ " with customer id "+accountResponse.getAccountId());

        // Publish Account Message to queue for other consumer.
        publisher.publishMessage(accountResponse);

        return accountResponse;
    }

    @Override
    public AccountResponse getAccount(int accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if(accountOptional.isPresent()) {
            log.debug("Return account details for "+ accountId);
            return convertToResponse(accountOptional.get());
        }

        log.debug("Account not found for "+accountId);
        throw new AccountNotFound(Constants.ACCOUNT_NOT_FOUND);
    }

    private AccountResponse convertToResponse(Account account) {
        return AccountResponse.builder()
                .accountId(account.getId())
                .customerId(account.getCustomerId())
                .country(account.getCountry())
                .balances(convertToBalanceResponse(account.getBalanceList()))
                .build();
    }

    private List<BalanceResponse> convertToBalanceResponse(List<Balance> balanceList) {
        return balanceList.stream().map(balance ->
                BalanceResponse.builder().ammount(balance.getAmmount()).currency(balance.getType()).build()
        ).collect(Collectors.toList());
    }

    private List<Balance> convertBalances(List<Currency> currencies) {
        return currencies.stream().map(currency ->
                Balance.builder().ammount(BigDecimal.ZERO).type(currency).build())
                .collect(Collectors.toList());
    }

}
