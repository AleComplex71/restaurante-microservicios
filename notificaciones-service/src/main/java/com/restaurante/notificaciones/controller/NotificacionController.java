package com.restaurante.notificaciones.controller;

import com.restaurante.notificaciones.dto.NotificacionDTO;
import com.restaurante.notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor
@Slf4j
public class NotificacionController {

    private final NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<NotificacionDTO> crearNotificacion(@Valid @RequestBody NotificacionDTO notificacionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.crearNotificacion(notificacionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionDTO> obtenerNotificacion(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.obtenerNotificacion(id));
    }

    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> listarNotificaciones() {
        return ResponseEntity.ok(notificacionService.listarNotificaciones());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.obtenerNotificacionesPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<List<NotificacionDTO>> obtenerNoLeidas(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.obtenerNoLeidas(usuarioId));
    }

    @PatchMapping("/{id}/leer")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable Long id) {
        notificacionService.marcarComoLeida(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/enviar")
    public ResponseEntity<Void> marcarComoEnviada(@PathVariable Long id) {
        notificacionService.marcarComoEnviada(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id) {
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}

