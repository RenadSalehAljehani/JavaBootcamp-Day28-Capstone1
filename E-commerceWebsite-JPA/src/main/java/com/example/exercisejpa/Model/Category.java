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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name can't be empty.")
    @Size(min = 4, message = "Name have to be more than 3 length long.")
    @Column(columnDefinition = "varchar(10) not null unique")
    @Check(constraints = "length(name) > 3")
    private String name;

    // Extra data
    @NotNull(message = "Allocation percentage can't be null.")
    @Positive(message = "Allocation percentage must be a positive number larger than zero.")
    @Min(value = 1, message = "Allocation percentage minimum value is 1.")
    @Column(columnDefinition = "double not null")
    @Check(constraints = "allocationPercentage > 0")
    private Double allocationPercentage;
}