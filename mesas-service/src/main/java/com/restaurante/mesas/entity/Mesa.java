package com.restaurante.mesas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "mesas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Column(nullable = false, length = 20)
    private String numero;

    @NotNull(message = "La capacidad es requerida")
    @Positive(message = "La capacidad debe ser mayor a 0")
    @Column(nullable = false)
    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMesa estado = EstadoMesa.DISPONIBLE;

    private String ubicacion;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean activa = true;

    public enum EstadoMesa {
        DISPONIBLE, OCUPADA, RESERVADA, MANTENIMIENTO
    }
}

