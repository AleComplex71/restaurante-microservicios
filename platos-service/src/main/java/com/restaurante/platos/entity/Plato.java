package com.restaurante.platos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "platos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del plato no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El precio no puede ser nulo")
    @Positive(message = "El precio debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(columnDefinition = "VARCHAR(50) DEFAULT 'Principal'")
    private String categoria = "Principal";

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean disponible = true;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer tiempoPreparacion = 0; // en minutos
}

