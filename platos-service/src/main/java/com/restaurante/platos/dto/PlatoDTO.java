package com.restaurante.platos.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatoDTO {
    
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es requerida")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es requerido")
    @Positive(message = "El precio debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "El precio debe ser un número válido")
    private BigDecimal precio;

    @NotBlank(message = "La categoría es requerida")
    private String categoria;

    private Boolean disponible = true;

    @Min(value = 5, message = "El tiempo mínimo es 5 minutos")
    @Max(value = 120, message = "El tiempo máximo es 120 minutos")
    private Integer tiempoPreparacion = 0;
}

