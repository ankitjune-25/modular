package com.ankit.modular.controller.request;

import com.ankit.modular.Utils.Constants;
import com.ankit.modular.enumuration.Currency;
import com.ankit.modular.enumuration.TransactionType;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class TransactionRequest {

    @NotBlank(message = Constants.ACCOUNT_MISSING)
    private int accountId;

    @Min(value = 0, message = Constants.INVALID_AMMOUNT)
    private BigDecimal ammount;

    @NotBlank(message = Constants.INVALID_CURRENCY)
    private Currency currency;

    @NotBlank(message = Constants.INVALID_DIRECTION)
    private TransactionType direction;

    @NotBlank(message = Constants.DISCRIPTION_MISSING)
    private String description;
}
