package com.restaurante.clientes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "^[0-9\\-\\+]{10,}$", message = "El teléfono debe contener al menos 10 dígitos")
    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(columnDefinition = "TEXT")
    private String direccion;

    @Column(length = 20)
    private String documento;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean activo = true;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer totalPedidos = 0;

    @Column(precision = 10, scale = 2)
    private BigDecimal gastTotal = BigDecimal.ZERO;

    private LocalDateTime fechaRegistro = LocalDateTime.now();

    private LocalDateTime ultimaCompra;
}

