package com.restaurante.empleados.controller;

import com.restaurante.empleados.dto.EmpleadoDTO;
import com.restaurante.empleados.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
@Slf4j
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @PostMapping
    public ResponseEntity<EmpleadoDTO> crearEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.crearEmpleado(empleadoDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> obtenerEmpleado(@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.obtenerEmpleado(id));
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> listarEmpleados() {
        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<EmpleadoDTO>> listarEmpleadosActivos() {
        return ResponseEntity.ok(empleadoService.listarEmpleadosActivos());
    }

    @GetMapping("/puesto/{puesto}")
    public ResponseEntity<List<EmpleadoDTO>> listarPorPuesto(@PathVariable String puesto) {
        return ResponseEntity.ok(empleadoService.listarPorPuesto(puesto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> actualizarEmpleado(@PathVariable Long id, @Valid @RequestBody EmpleadoDTO empleadoDTO) {
        return ResponseEntity.ok(empleadoService.actualizarEmpleado(id, empleadoDTO));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarEmpleado(@PathVariable Long id) {
        empleadoService.desactivarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}

