package com.example.ecommercewebsite.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    @NotEmpty(message = "Id can't be null.")
    private String id;
    @NotEmpty(message = "Username can't be null.")
    @Size(min = 6, message = "Username have to be more than 5 length long.")
    private String username;
    @NotEmpty(message = "Password can't be null.")
    @Size(min = 7, message = "Password have to be more than 6 length long.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$" , message = "Password must have characters and digits")
    private String password;
    @NotEmpty(message = "Email can't be null.")
    @Email
    private String email;
    @NotEmpty(message = "Role can't be null.")
    @Pattern(regexp = "^(?i)(Admin|Customer)$" , message = "Role must be only admin or customer.")
    private String role;
    @NotNull(message = "Balance can't be null.")
    @Positive(message = "Balance have to be a positive number.")
    private double balance;
    // Extra data
    @AssertFalse(message = "Is spun initial value must be false.")
    private boolean isSpun;
    @NotNull(message = "Total budget can't be null.")
    @Positive(message = "Total budget have to be a positive number.")
    private double totalBudget;
}
