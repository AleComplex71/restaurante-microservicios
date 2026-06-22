package com.restaurante.empleados.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El email es requerido")
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "El teléfono es requerido")
    @Column(nullable = false, length = 20)
    private String telefono;

    @NotBlank(message = "El puesto es requerido")
    @Column(nullable = false, length = 100)
    private String puesto;

    @NotNull(message = "El salario es requerido")
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoContrato tipoContrato = TipoContrato.TIEMPO_COMPLETO;

    @Column(nullable = false)
    private LocalDate fechaIngreso;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean activo = true;

    public enum TipoContrato {
        TIEMPO_COMPLETO, TIEMPO_PARCIAL, TEMPORAL, POR_HORAS
    }
}

