package com.restaurante.inventario.controller;

import com.restaurante.inventario.dto.IngredienteDTO;
import com.restaurante.inventario.service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventario")
@RequiredArgsConstructor
@Slf4j
public class InventarioController {

    private final InventarioService inventarioService;

    @PostMapping
    public ResponseEntity<IngredienteDTO> agregarIngrediente(@Valid @RequestBody IngredienteDTO ingredienteDTO) {
        log.info("POST - Agregar ingrediente: {}", ingredienteDTO.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.agregarIngrediente(ingredienteDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredienteDTO> obtenerIngrediente(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.obtenerIngrediente(id));
    }

    @GetMapping
    public ResponseEntity<List<IngredienteDTO>> listarIngredientes() {
        return ResponseEntity.ok(inventarioService.listarIngredientes());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<IngredienteDTO>> listarDisponibles() {
        return ResponseEntity.ok(inventarioService.listarDisponibles());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<IngredienteDTO>> buscarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(inventarioService.buscarPorCategoria(categoria));
    }

    @GetMapping("/bajo-stock")
    public ResponseEntity<List<IngredienteDTO>> obtenerBajoStock() {
        return ResponseEntity.ok(inventarioService.obtenerBajoStock());
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredienteDTO> actualizar(@PathVariable Long id, @Valid @RequestBody IngredienteDTO ingredienteDTO) {
        return ResponseEntity.ok(inventarioService.actualizarIngrediente(id, ingredienteDTO));
    }

    @PatchMapping("/{id}/descontar")
    public ResponseEntity<Void> descontar(@PathVariable Long id, @RequestParam Double cantidad) {
        inventarioService.descontarStock(id, cantidad);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/aumentar")
    public ResponseEntity<Void> aumentar(@PathVariable Long id, @RequestParam Double cantidad) {
        inventarioService.aumentarStock(id, cantidad);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioService.eliminarIngrediente(id);
        return ResponseEntity.noContent().build();
    }
}

