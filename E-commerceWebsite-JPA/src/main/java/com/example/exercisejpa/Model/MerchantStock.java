package com.example.exercisejpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class MerchantStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Product id can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer product_id;

    @NotNull(message = "Merchant id can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer merchant_id;

    @NotNull(message = "Stock can't be empty.")
    @Min(value = 11, message = "Stock have to be more than 10 at start.")
    @Column(columnDefinition = "int not null")
    @Check(constraints = "stock>10")
    private Integer stock;
}