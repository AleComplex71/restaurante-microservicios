package com.restaurante.mesas.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private Long id;

    @NotNull(message = "Mesa ID requerido")
    private Long mesaId;

    @NotNull(message = "Cliente ID requerido")
    private Long clienteId;

    @NotNull(message = "Fecha requerida")
    private LocalDateTime fechaReserva;

    @NotNull(message = "Cantidad requerida")
    @Positive
    private Integer cantidadPersonas;

    private String estado;

    private String notas;

    private LocalDateTime fechaCreacion;
}

