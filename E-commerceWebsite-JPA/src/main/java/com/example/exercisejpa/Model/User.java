package com.example.exercisejpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username can't be empty.")
    @Size(min = 6, message = "Username have to be more than 5 length long.")
    @Column(columnDefinition = "varchar(10) not null unique")
    @Check(constraints = "length(username) > 5")
    private String username;

    @NotEmpty(message = "Password can't be empty.")
    @Size(min = 7, message = "Username have to be more than 6 length long.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{7,}$", message = "Password must have characters and digits.")
    @Column(columnDefinition = "varchar(20) not null unique")
    @Check(constraints = "length(password) > 6")
    private String password;

    @Email(message = "Invalid email format.")
    @NotEmpty(message = "Email can't be empty.")
    @Column(columnDefinition = "varchar(255) not null unique")
    @Check(constraints = "Email LIKE '_%@_%._%'")
    private String Email;

    @NotEmpty(message = "Role can't be empty.")
    @Pattern(regexp = "^(?i)(Admin|Customer)$", message = "Role must be 'Admin' or 'Customer'.")
    @Column(columnDefinition = "varchar(8) not null")
    @Check(constraints = "LOWER(role) = 'admin' OR LOWER(role) = 'customer'")
    private String role;

    @NotNull(message = "Balance can't be empty.")
    @PositiveOrZero(message = "Balance must be a positive number or zero.")
    @Column(columnDefinition = "double not null")
    @Check(constraints = "balance >= 0")
    private Double balance;

    // Extra data
    @AssertFalse(message = "The initial value of 'isSpun' must be false.")
    @Column(columnDefinition = "boolean default false")
    @Check(constraints = "isSpun = false")
    private Boolean isSpun;

    @NotNull(message = "Total budget can't be null.")
    @PositiveOrZero(message = "Total budget must be a positive number or zero.")
    @Column(columnDefinition = "double not null")
    @Check(constraints = "totalBudget >= 0")
    private Double totalBudget;
}