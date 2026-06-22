package com.restaurante.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/platos")
    public ResponseEntity<Map<String, Object>> platosFallback() {
        return createFallbackResponse("Platos Service");
    }

    @GetMapping("/pedidos")
    public ResponseEntity<Map<String, Object>> pedidosFallback() {
        return createFallbackResponse("Pedidos Service");
    }

    @GetMapping("/clientes")
    public ResponseEntity<Map<String, Object>> clientesFallback() {
        return createFallbackResponse("Clientes Service");
    }

    @GetMapping("/pagos")
    public ResponseEntity<Map<String, Object>> pagosFallback() {
        return createFallbackResponse("Pagos Service");
    }

    @GetMapping("/inventario")
    public ResponseEntity<Map<String, Object>> inventarioFallback() {
        return createFallbackResponse("Inventario Service");
    }

    @GetMapping("/mesas")
    public ResponseEntity<Map<String, Object>> mesasFallback() {
        return createFallbackResponse("Mesas Service");
    }

    @GetMapping("/empleados")
    public ResponseEntity<Map<String, Object>> empleadosFallback() {
        return createFallbackResponse("Empleados Service");
    }

    @GetMapping("/notificaciones")
    public ResponseEntity<Map<String, Object>> notificacionesFallback() {
        return createFallbackResponse("Notificaciones Service");
    }

    @GetMapping("/reportes")
    public ResponseEntity<Map<String, Object>> reportesFallback() {
        return createFallbackResponse("Reportes Service");
    }

    private ResponseEntity<Map<String, Object>> createFallbackResponse(String serviceName) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Service Unavailable");
        response.put("message", serviceName + " is currently unavailable. Please try again later.");
        response.put("status", 503);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
