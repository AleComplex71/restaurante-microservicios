package com.restaurante.platos.controller;

import com.restaurante.platos.dto.PlatoDTO;
import com.restaurante.platos.service.PlatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platos")
@RequiredArgsConstructor
@Slf4j
public class PlatoController {

    private final PlatoService platoService;

    @PostMapping
    public ResponseEntity<PlatoDTO> crearPlato(@Valid @RequestBody PlatoDTO platoDTO) {
        log.info("POST /api/v1/platos - Crear nuevo plato: {}", platoDTO.getNombre());
        PlatoDTO creado = platoService.crearPlato(platoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatoDTO> obtenerPlato(@PathVariable Long id) {
        log.info("GET /api/v1/platos/{} - Obtener plato", id);
        return ResponseEntity.ok(platoService.obtenerPlato(id));
    }

    @GetMapping
    public ResponseEntity<List<PlatoDTO>> listarPlatos() {
        log.info("GET /api/v1/platos - Listar todos los platos");
        return ResponseEntity.ok(platoService.listarPlatos());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<PlatoDTO>> listarPlatosDisponibles() {
        log.info("GET /api/v1/platos/disponibles - Listar platos disponibles");
        return ResponseEntity.ok(platoService.listarPlatosDisponibles());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<PlatoDTO>> buscarPorCategoria(@PathVariable String categoria) {
        log.info("GET /api/v1/platos/categoria/{} - Buscar por categoría", categoria);
        return ResponseEntity.ok(platoService.buscarPorCategoria(categoria));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<PlatoDTO>> buscarPorNombre(@RequestParam String nombre) {
        log.info("GET /api/v1/platos/buscar?nombre={} - Buscar por nombre", nombre);
        return ResponseEntity.ok(platoService.buscarPorNombre(nombre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatoDTO> actualizarPlato(
            @PathVariable Long id,
            @Valid @RequestBody PlatoDTO platoDTO) {
        log.info("PUT /api/v1/platos/{} - Actualizar plato", id);
        return ResponseEntity.ok(platoService.actualizarPlato(id, platoDTO));
    }

    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<Void> cambiarDisponibilidad(
            @PathVariable Long id,
            @RequestParam Boolean disponible) {
        log.info("PATCH /api/v1/platos/{}/disponibilidad - Cambiar disponibilidad a: {}", id, disponible);
        platoService.cambiarDisponibilidad(id, disponible);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlato(@PathVariable Long id) {
        log.info("DELETE /api/v1/platos/{} - Eliminar plato", id);
        platoService.eliminarPlato(id);
        return ResponseEntity.noContent().build();
    }
}

