package com.restaurante.pagos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El pedido ID no puede ser nulo")
    @Column(nullable = false)
    private Long pedidoId;

    @NotNull(message = "El cliente ID no puede ser nulo")
    @Column(nullable = false)
    private Long clienteId;

    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPago metodoPago = MetodoPago.EFECTIVO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado = EstadoPago.PENDING;

    @Column(length = 100)
    private String numeroTransaccion;

    private LocalDateTime fechaPago = LocalDateTime.now();

    private String referencia;

    public enum MetodoPago {
        EFECTIVO, TARJETA_CREDITO, TARJETA_DEBITO, TRANSFERENCIA, QR
    }

    public enum EstadoPago {
        PENDING, COMPLETED, FAILED, CANCELLED, REFUNDED
    }
}

