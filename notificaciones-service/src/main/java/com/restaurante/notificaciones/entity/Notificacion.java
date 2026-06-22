package com.restaurante.notificaciones.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El usuario ID es requerido")
    @Column(nullable = false)
    private Long usuarioId;

    @NotBlank(message = "El tipo es requerido")
    @Column(nullable = false, length = 50)
    private String tipo;

    @NotBlank(message = "El asunto es requerido")
    @Column(nullable = false, length = 200)
    private String asunto;

    @NotBlank(message = "El mensaje es requerido")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String mensaje;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean leida = false;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean enviada = false;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private LocalDateTime fechaEnvio;

    private LocalDateTime fechaLectura;
}

