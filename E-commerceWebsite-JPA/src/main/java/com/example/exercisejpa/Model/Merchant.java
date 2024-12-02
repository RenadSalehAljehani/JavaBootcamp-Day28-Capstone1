package com.example.exercisejpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name can't be empty.")
    @Size(min = 4, message = "Name have to be more than 3 length long.")
    @Column(columnDefinition = "varchar(10) not null unique")
    @Check(constraints = "length(name) > 3")
    private String name;
}