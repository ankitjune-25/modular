package com.ankit.modular.service;

import com.ankit.modular.controller.request.AccountRequest;
import com.ankit.modular.controller.request.TransactionRequest;
import com.ankit.modular.controller.response.AccountResponse;
import com.ankit.modular.controller.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse createTransaction(TransactionRequest transactionrequest);

    List<TransactionResponse> getTransaction(int accountId);
}
