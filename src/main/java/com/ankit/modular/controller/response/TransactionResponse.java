package com.ankit.modular.controller.response;

import com.ankit.modular.enumuration.Currency;
import com.ankit.modular.enumuration.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionResponse {
    private int accountId;

    private int transactionId;

    private TransactionType direction;

    private BigDecimal amount;

    private BigDecimal balance;

    private Currency currency;

    private String description;

}
