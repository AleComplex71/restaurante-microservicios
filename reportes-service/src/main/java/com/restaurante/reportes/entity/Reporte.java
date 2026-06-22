package com.restaurante.reportes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo es requerido")
    @Column(nullable = false, length = 100)
    private String tipo;

    @NotBlank(message = "El título es requerido")
    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "LONGTEXT")
    private String contenido;

    @Column(nullable = false)
    private LocalDateTime fechaGeneracion = LocalDateTime.now();

    @Column(length = 50)
    private String formato;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalVentas;

    @Column
    private Integer cantidadPedidos;

    @Column(precision = 10, scale = 2)
    private BigDecimal promedioPedido;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean completado = false;
}

