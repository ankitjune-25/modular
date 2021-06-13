package com.ankit.modular.controller;

import com.ankit.modular.controller.request.AccountRequest;
import com.ankit.modular.controller.request.TransactionRequest;
import com.ankit.modular.controller.response.AccountResponse;
import com.ankit.modular.controller.response.TransactionResponse;
import com.ankit.modular.service.AccountService;
import com.ankit.modular.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLConnection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public TransactionResponse createTransaction(@RequestBody TransactionRequest request) {
        log.info("Request for creating transaction for customer "+request.getAccountId());
        return transactionService.createTransaction(request);

    }

    @GetMapping("/{accountId}")
    public List<TransactionResponse> getTransaction(@PathVariable("accountId") int accountId) {
        log.info("Request for getting transaction for "+accountId);
        return transactionService.getTransaction(accountId);
    }
}
