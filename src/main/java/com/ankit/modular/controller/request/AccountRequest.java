package com.ankit.modular.controller.request;

import com.ankit.modular.Utils.Constants;
import com.ankit.modular.enumuration.Currency;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AccountRequest {

    @NotBlank(message = Constants.CUSTOMER_ID_MISSING)
    private String customerId;

    @NotBlank(message = Constants.COUNTRY_MISSING)
    private String country;

    @Size(min = 1, message = Constants.INVALID_CURRENCY)
    private List<Currency> currencies;
}
