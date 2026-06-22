package com.restaurante.pedidos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El cliente ID no puede ser nulo")
    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @NotNull(message = "El estado no puede ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado = EstadoPedido.PENDIENTE;

    @NotNull(message = "El total no puede ser nulo")
    @Positive(message = "El total debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id")
    private List<DetallePedido> detalles;

    @Column(length = 255)
    private String notas;

    @Column(nullable = false)
    private LocalDateTime ultimaActualizacion = LocalDateTime.now();

    @PreUpdate
    public void actualizarTimestamp() {
        this.ultimaActualizacion = LocalDateTime.now();
    }

    public enum EstadoPedido {
        PENDIENTE, EN_PREPARACION, LISTO, ENTREGADO, CANCELADO
    }
}

