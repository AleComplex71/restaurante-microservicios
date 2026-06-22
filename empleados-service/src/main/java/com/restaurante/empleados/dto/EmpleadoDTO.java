package com.restaurante.empleados.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {

    private Long id;

    @NotBlank(message = "Nombre requerido")
    private String nombre;

    @NotBlank
    @Email
    private String email;

    @NotBlank(message = "Teléfono requerido")
    private String telefono;

    @NotBlank(message = "Puesto requerido")
    private String puesto;

    @NotNull(message = "Salario requerido")
    @Positive
    private BigDecimal salario;

    private String tipoContrato;

    private LocalDate fechaIngreso;

    private Boolean activo;
}

