package com.example.ecommercewebsite.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "Id can't be null.")
    private String id;
    @NotEmpty(message = "Product id can't be null.")
    private String product_id;
    @NotEmpty(message = "Merchant id can't be null.")
    private String merchant_id;
    @NotNull(message = "Stock can't be null.")
    @Min(value = 11, message = "Stock have to be more than 10 at start.")
    private int stock;
}