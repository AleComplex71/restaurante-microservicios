package com.restaurante.pedidos.controller;

import com.restaurante.pedidos.dto.PedidoDTO;
import com.restaurante.pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
@Slf4j
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(@Valid @RequestBody PedidoDTO pedidoDTO) {
        log.info("POST /api/v1/pedidos - Crear nuevo pedido para cliente: {}", pedidoDTO.getClienteId());
        PedidoDTO creado = pedidoService.crearPedido(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Long id) {
        log.info("GET /api/v1/pedidos/{} - Obtener pedido", id);
        return ResponseEntity.ok(pedidoService.obtenerPedido(id));
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarPedidos() {
        log.info("GET /api/v1/pedidos - Listar todos los pedidos");
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoDTO>> obtenerPedidosPorCliente(@PathVariable Long clienteId) {
        log.info("GET /api/v1/pedidos/cliente/{} - Obtener pedidos del cliente", clienteId);
        return ResponseEntity.ok(pedidoService.obtenerPedidosPorCliente(clienteId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoDTO>> obtenerPedidosPorEstado(@PathVariable String estado) {
        log.info("GET /api/v1/pedidos/estado/{} - Obtener pedidos por estado", estado);
        return ResponseEntity.ok(pedidoService.obtenerPedidosPorEstado(estado));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado) {
        log.info("PATCH /api/v1/pedidos/{}/estado - Actualizar estado a: {}", id, nuevoEstado);
        return ResponseEntity.ok(pedidoService.actualizarEstadoPedido(id, nuevoEstado));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        log.info("PATCH /api/v1/pedidos/{}/cancelar - Cancelar pedido", id);
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        log.info("DELETE /api/v1/pedidos/{} - Eliminar pedido", id);
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}

