package com.restaurante.mesas.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MesaDTO {

    private Long id;

    @NotBlank(message = "El número es requerido")
    private String numero;

    @NotNull(message = "La capacidad es requerida")
    @Positive
    private Integer capacidad;

    private String estado;

    private String ubicacion;

    private Boolean activa;
}

