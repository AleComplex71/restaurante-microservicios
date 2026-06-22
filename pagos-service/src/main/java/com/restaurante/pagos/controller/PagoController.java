package com.restaurante.pagos.controller;

import com.restaurante.pagos.dto.PagoDTO;
import com.restaurante.pagos.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
@Slf4j
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<PagoDTO> procesarPago(@Valid @RequestBody PagoDTO pagoDTO) {
        log.info("POST - Procesar pago de ${}", pagoDTO.getMonto());
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.procesarPago(pagoDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtenerPago(@PathVariable Long id) {
        log.info("GET - Obtener pago {}", id);
        return ResponseEntity.ok(pagoService.obtenerPago(id));
    }

    @GetMapping
    public ResponseEntity<List<PagoDTO>> listarPagos() {
        log.info("GET - Listar pagos");
        return ResponseEntity.ok(pagoService.listarPagos());
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<PagoDTO>> obtenerPagosPorPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorPedido(pedidoId));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PagoDTO>> obtenerPagosPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorCliente(clienteId));
    }

    @PatchMapping("/{id}/reembolsar")
    public ResponseEntity<PagoDTO> reembolsarPago(@PathVariable Long id) {
        log.info("PATCH - Reembolsar pago {}", id);
        return ResponseEntity.ok(pagoService.reembolsarPago(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarPago(@PathVariable Long id) {
        log.info("PATCH - Cancelar pago {}", id);
        pagoService.cancelarPago(id);
        return ResponseEntity.noContent().build();
    }
}

