package com.restaurante.mesas.controller;

import com.restaurante.mesas.dto.MesaDTO;
import com.restaurante.mesas.dto.ReservaDTO;
import com.restaurante.mesas.service.MesasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mesas")
@RequiredArgsConstructor
@Slf4j
public class MesasController {

    private final MesasService mesasService;

    @PostMapping
    public ResponseEntity<MesaDTO> crearMesa(@Valid @RequestBody MesaDTO mesaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mesasService.crearMesa(mesaDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MesaDTO> obtenerMesa(@PathVariable Long id) {
        return ResponseEntity.ok(mesasService.obtenerMesa(id));
    }

    @GetMapping
    public ResponseEntity<List<MesaDTO>> listarMesas() {
        return ResponseEntity.ok(mesasService.listarMesas());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<MesaDTO>> obtenerMesasDisponibles() {
        return ResponseEntity.ok(mesasService.obtenerMesasDisponibles());
    }

    @GetMapping("/capacidad/{capacidad}")
    public ResponseEntity<MesaDTO> obtenerMesaPorCapacidad(@PathVariable Integer capacidad) {
        return ResponseEntity.ok(mesasService.obtenerMesaPorCapacidad(capacidad));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<MesaDTO> cambiarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(mesasService.cambiarEstadoMesa(id, nuevoEstado));
    }

    @PostMapping("/reservas")
    public ResponseEntity<ReservaDTO> crearReserva(@Valid @RequestBody ReservaDTO reservaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mesasService.crearReserva(reservaDTO));
    }

    @GetMapping("/reservas/{id}")
    public ResponseEntity<ReservaDTO> obtenerReserva(@PathVariable Long id) {
        return ResponseEntity.ok(mesasService.obtenerReserva(id));
    }

    @GetMapping("/reservas")
    public ResponseEntity<List<ReservaDTO>> listarReservas() {
        return ResponseEntity.ok(mesasService.listarReservas());
    }

    @GetMapping("/{mesaId}/reservas")
    public ResponseEntity<List<ReservaDTO>> obtenerReservasPorMesa(@PathVariable Long mesaId) {
        return ResponseEntity.ok(mesasService.obtenerReservasPorMesa(mesaId));
    }

    @PatchMapping("/reservas/{id}/estado")
    public ResponseEntity<ReservaDTO> actualizarEstadoReserva(@PathVariable Long id, @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(mesasService.actualizarEstadoReserva(id, nuevoEstado));
    }
}

