package com.ankit.modular.controller.response;

import com.ankit.modular.enumuration.Currency;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BalanceResponse {

    private Currency currency;

    private BigDecimal ammount;
}
