package com.example.exercisejpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name can't be empty.")
    @Size(min = 4, message = "Name have to be more than 3 length long.")
    @Column(columnDefinition = "varchar(10) not null unique")
    @Check(constraints = "length(name) > 3")
    private String name;

    @NotNull(message = "Price can't be empty.")
    @Positive(message = "Price must be a positive number.")
    @Column(columnDefinition = "double not null")
    @Check(constraints = "price > 0")
    private Double price;

    @NotNull(message = "Category id can't be empty.")
    private Integer categoryID;

    // Extra data
    @NotNull(message = "Age can't be null.")
    @Positive(message = "Age must be a positive number larger than zero.")
    @Column(columnDefinition = "int not null")
    @Check(constraints = "age > 0")
    private Integer age;
}