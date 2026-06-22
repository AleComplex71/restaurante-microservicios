package com.restaurante.clientes.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotBlank(message = "El teléfono es requerido")
    @Pattern(regexp = "^[0-9\\-\\+]{10,}$", message = "El teléfono debe ser válido")
    private String telefono;

    private String direccion;

    private String documento;

    private Boolean activo = true;

    private Integer totalPedidos;

    private BigDecimal gastTotal;

    private LocalDateTime fechaRegistro;

    private LocalDateTime ultimaCompra;
}

