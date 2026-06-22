package com.restaurante.pagos.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    private Long id;

    @NotNull(message = "El pedido ID es requerido")
    private Long pedidoId;

    @NotNull(message = "El cliente ID es requerido")
    private Long clienteId;

    @NotNull(message = "El monto es requerido")
    @Positive(message = "El monto debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "Formato de monto inválido")
    private BigDecimal monto;

    @NotNull(message = "El método de pago es requerido")
    private String metodoPago;

    private String estado;

    private String numeroTransaccion;

    private LocalDateTime fechaPago;

    private String referencia;
}

