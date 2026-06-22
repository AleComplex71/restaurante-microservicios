package com.restaurante.pedidos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El plato ID no puede ser nulo")
    @Column(nullable = false)
    private Long platoId;

    @NotBlank(message = "El nombre del plato no puede estar vacío")
    @Column(nullable = false)
    private String platoNombre;

    @NotNull(message = "La cantidad no puede ser nula")
    @Positive(message = "La cantidad debe ser mayor a 0")
    @Column(nullable = false)
    private Integer cantidad;

    @NotNull(message = "El precio unitario no puede ser nulo")
    @Positive(message = "El precio debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(length = 255)
    private String notas;

    @PrePersist
    public void calcularSubtotal() {
        if (this.precioUnitario != null) {
            this.subtotal = this.precioUnitario.multiply(java.math.BigDecimal.valueOf(this.cantidad));
        } else {
            this.subtotal = java.math.BigDecimal.ZERO;
        }
    }

    @PreUpdate
    public void actualizarSubtotal() {
        calcularSubtotal();
    }
}

