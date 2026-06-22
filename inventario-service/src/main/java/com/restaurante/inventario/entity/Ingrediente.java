package com.restaurante.inventario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ingredientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor a 0")
    @Column(nullable = false)
    private Double cantidad;

    @NotBlank(message = "La unidad es requerida")
    @Column(nullable = false, length = 20)
    private String unidad;

    @NotNull(message = "El costo es requerido")
    @PositiveOrZero(message = "El costo no puede ser negativo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costo;

    @NotNull(message = "El stock mínimo es requerido")
    @Positive(message = "El stock mínimo debe ser mayor a 0")
    @Column(nullable = false)
    private Double stockMinimo;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean disponible = true;

    @Column(nullable = false)
    private String categoria;
}

