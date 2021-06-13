package com.ankit.modular.service;

import com.ankit.modular.AbstractTest;
import com.ankit.modular.controller.request.AccountRequest;
import com.ankit.modular.controller.response.AccountResponse;
import com.ankit.modular.entity.Account;
import com.ankit.modular.publisher.MessagePublisher;
import com.ankit.modular.repository.AccountRepository;
import com.ankit.modular.service.impl.AccountServiceImpl;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AccountServiceImpl.class})
public class AccountServiceTest extends AbstractTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private MessagePublisher publisher;

    @Test
    public void createAccountTest() throws JSONException {
        AccountRequest accountRequest = gson.fromJson(jsonRequest.getString("createAccount"), AccountRequest.class);
        Account account = gson.fromJson(jsonMock.getString("createAccountMock"), Account.class);
        when(accountRepository.save(any())).thenReturn(account);
        AccountResponse accountResponse = accountService.createAccount(accountRequest);
        assertEquals(gson.fromJson(jsonResponse.getString("createAccountResponse"),AccountResponse.class), accountResponse, "Error occur in creating account.");
    }

    @Test
    public void getAccountTest() throws JSONException {
        Account account = gson.fromJson(jsonMock.getString("getAccountMock"), Account.class);
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        AccountResponse accountResponse = accountService.getAccount(1);
        assertEquals(gson.fromJson(jsonResponse.getString("getAccountResponse"),AccountResponse.class), accountResponse, "Error occur getting account.");
    }
}
