package com.restaurante.notificaciones.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {

    private Long id;

    @NotNull(message = "Usuario ID requerido")
    private Long usuarioId;

    @NotBlank(message = "Tipo requerido")
    private String tipo;

    @NotBlank(message = "Asunto requerido")
    private String asunto;

    @NotBlank(message = "Mensaje requerido")
    private String mensaje;

    private Boolean leida;

    private Boolean enviada;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaEnvio;

    private LocalDateTime fechaLectura;
}

