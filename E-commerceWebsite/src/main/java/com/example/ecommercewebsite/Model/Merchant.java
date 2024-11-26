package com.example.ecommercewebsite.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {
    @NotEmpty(message = "Id can't be null.")
    private String id;
    @NotEmpty(message = "Name can't be null.")
    @Size(min = 4, message = "Name have to be more than 3 length long.")
    private String name;
}