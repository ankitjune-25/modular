package com.ankit.modular.controller;

import com.ankit.modular.controller.request.AccountRequest;
import com.ankit.modular.controller.response.AccountResponse;
import com.ankit.modular.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private AccountService accountService;

    @PostMapping
    public AccountResponse createAccount(@Valid @RequestBody AccountRequest request) {
        log.info("Request for creating account for customer "+request.getCustomerId());
        return accountService.createAccount(request);

    }

    @GetMapping("/{accountId}")
    public AccountResponse getAccount(@PathVariable("accountId") int accountId) {
        log.info("Request for getting account for "+accountId);
        return accountService.getAccount(accountId);
    }
}
