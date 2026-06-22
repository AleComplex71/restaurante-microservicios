package com.restaurante.pedidos.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoDTO {

    private Long id;

    @NotNull(message = "El plato ID es requerido")
    private Long platoId;

    private String platoNombre;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    private BigDecimal precioUnitario;

    private BigDecimal subtotal;

    @Size(max = 255, message = "Las notas no pueden exceder 255 caracteres")
    private String notas;
}

