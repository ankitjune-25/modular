package com.ankit.modular.service;

import com.ankit.modular.controller.request.AccountRequest;
import com.ankit.modular.controller.response.AccountResponse;

public interface AccountService {

    AccountResponse createAccount(AccountRequest accountRequest);

    AccountResponse getAccount(int accountId);
}
