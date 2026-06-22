package com.restaurante.reportes.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDTO {

    private Long id;

    @NotBlank(message = "Tipo requerido")
    private String tipo;

    @NotBlank(message = "Título requerido")
    private String titulo;

    private String descripcion;

    private String contenido;

    private LocalDateTime fechaGeneracion;

    private String formato;

    private BigDecimal totalVentas;

    private Integer cantidadPedidos;

    private BigDecimal promedioPedido;

    private Boolean completado;
}

