package com.example.ecommercewebsite.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty(message = "Id can't be null.")
    private String id;
    @NotEmpty(message = "Name can't be null.")
    @Size(min = 4, message = "Name have to be more than 3 length long.")
    private String name;
    @NotNull(message = "Price can't be null.")
    @Positive(message = "Price must be a positive number.")
    private double price;
    @NotEmpty(message = "Category id can't be null.")
    private String categoryID;
    // Extra data
    @NotNull(message = "Age can't be null.")
    @Positive(message = "Age must be a positive number larger than zero.")
    private int age;
}
