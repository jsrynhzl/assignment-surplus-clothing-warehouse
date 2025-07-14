package com.example.clotheswarehouse.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

// Assignment 2
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Clothing {

    // Using JPA, we have to provide correct @Id annotation and use @GeneratedValue
    // to auto-generate the id, see the imports
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "clothing name is required")
    private String name;

    @Min(value = 2022, message = "year must be after 2021")
    private int yearOfCreation;

    @NotNull(message = "must not be empty and cost at least 1001")
    @DecimalMin(value = "1001", message = "must not be empty and cost at least 1001")
    private BigDecimal price;

    @NotNull(message = "brand must be selected")
    @Enumerated(EnumType.STRING)
    private Brand brand;

    @Builder.Default
    private LocalDate createdAt = LocalDate.now();

    public enum Brand {
        CHANEL("Chanel"), DIOR("Dior"), GUCCI("Gucci"), BALENCIAGA("Balenciaga");

        private final String name;

        private Brand(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
