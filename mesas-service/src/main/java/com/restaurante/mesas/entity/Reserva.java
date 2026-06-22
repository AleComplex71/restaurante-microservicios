package com.restaurante.mesas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La mesa ID es requerida")
    @Column(nullable = false)
    private Long mesaId;

    @NotNull(message = "El cliente ID es requerido")
    @Column(nullable = false)
    private Long clienteId;

    @NotNull(message = "La fecha es requerida")
    @Column(nullable = false)
    private LocalDateTime fechaReserva;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor a 0")
    @Column(nullable = false)
    private Integer cantidadPersonas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReserva estado = EstadoReserva.PENDIENTE;

    private String notas;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public enum EstadoReserva {
        PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA
    }
}

