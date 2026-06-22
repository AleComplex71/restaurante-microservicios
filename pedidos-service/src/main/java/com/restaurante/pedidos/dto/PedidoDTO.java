package com.restaurante.pedidos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;

    @NotNull(message = "El cliente ID es requerido")
    private Long clienteId;

    private LocalDateTime fechaCreacion;

    @NotNull(message = "El estado es requerido")
    private String estado;

    private BigDecimal total;

    @NotEmpty(message = "El pedido debe tener al menos un detalle")
    @Valid
    private List<DetallePedidoDTO> detalles;

    private String notas;

    private LocalDateTime ultimaActualizacion;
}

