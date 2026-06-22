package com.restaurante.inventario.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredienteDTO {

    private Long id;

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    private String descripcion;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "Cantidad debe ser mayor a 0")
    private Double cantidad;

    @NotBlank(message = "La unidad es requerida")
    private String unidad;

    @NotNull(message = "El costo es requerido")
    @PositiveOrZero
    private BigDecimal costo;

    @NotNull(message = "Stock mínimo requerido")
    @Positive
    private Double stockMinimo;

    private Boolean disponible;

    private String categoria;
}

