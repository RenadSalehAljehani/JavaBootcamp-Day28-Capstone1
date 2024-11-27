package com.example.ecommercewebsite.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {
    @NotEmpty(message = "Id can't be null.")
    private String id;
    @NotEmpty(message = "Name can't be null.")
    @Size(min = 4, message = "Name have to be more than 3 length long.")
    private String name;
    // Extra data
    @NotNull(message = "Allocation percentage can't be null.")
    @Positive(message = "Allocation percentage must be a positive number larger than zero.")
    private double allocationPercentage;
}
