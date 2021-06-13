package com.ankit.modular.service;

import com.ankit.modular.AbstractTest;
import com.ankit.modular.controller.request.TransactionRequest;
import com.ankit.modular.controller.response.TransactionResponse;
import com.ankit.modular.entity.Account;
import com.ankit.modular.entity.Transaction;
import com.ankit.modular.repository.AccountRepository;
import com.ankit.modular.repository.TransactionRepository;
import com.ankit.modular.service.impl.TransactionServiceImpl;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@ContextConfiguration(classes = {TransactionServiceImpl.class})
public class TransactionServiceTest extends AbstractTest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    public void createTransactionCreditTest() throws JSONException {
        TransactionRequest transactionRequest = gson.fromJson(jsonRequest.getString("createTransaction"), TransactionRequest.class);
        Transaction transaction = gson.fromJson(jsonMock.getString("inTransactionMock"), Transaction.class);
        Account account = gson.fromJson(jsonMock.getString("inAccountMock"), Account.class);

        when(transactionRepository.save(any())).thenReturn(transaction);
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        TransactionResponse transactionResponse = transactionService.createTransaction(transactionRequest);
        assertEquals(gson.fromJson(jsonResponse.getString("inTransactionResponse"), TransactionResponse.class), transactionResponse, "Error occur in making credit transaction.");
    }

    @Test
    public void createTransactionDebitTest() throws JSONException {
        TransactionRequest transactionRequest = gson.fromJson(jsonRequest.getString("createTransaction"), TransactionRequest.class);
        Transaction transaction = gson.fromJson(jsonMock.getString("outTransactionMock"), Transaction.class);
        Account account = gson.fromJson(jsonMock.getString("outAccountMock"), Account.class);
        when(transactionRepository.save(any())).thenReturn(transaction);
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        TransactionResponse transactionResponse = transactionService.createTransaction(transactionRequest);
        assertEquals(gson.fromJson(jsonResponse.getString("outTransactionResponse"), TransactionResponse.class), transactionResponse, "Error occur in making debit transaction.");
    }

    @Test
    public void getTransactionTest() throws JSONException {
        List<Transaction> transaction = gson.fromJson(
                jsonMock.getString("getTransactionMock"), new TypeToken<List<Transaction>>() {
                }.getType());
        when(transactionRepository.findByAccountId(anyInt())).thenReturn(transaction);
        List<TransactionResponse> transactionResponse = transactionService.getTransaction(1);
        assertEquals(gson.fromJson(jsonResponse.getString("getTransactionResponse"),  new TypeToken<List<TransactionResponse>>() {
        }.getType()), transactionResponse, "Error occur getting transaction.");
    }
}
