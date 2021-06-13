package com.ankit.modular.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountResponse {

    private int accountId;

    private String customerId;

    private String country;

    private List<BalanceResponse> balances;
}
