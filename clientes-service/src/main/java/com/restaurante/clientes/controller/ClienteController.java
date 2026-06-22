package com.restaurante.clientes.controller;

import com.restaurante.clientes.dto.ClienteDTO;
import com.restaurante.clientes.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Clientes", description = "API para gestión de clientes del restaurante")
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Crear nuevo cliente", description = "Registra un nuevo cliente en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
            content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
        @ApiResponse(responseCode = "409", description = "Email duplicado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        log.info("POST /api/v1/clientes - Crear cliente: {}", clienteDTO.getEmail());
        ClienteDTO creado = clienteService.crearCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Obtener cliente por ID", description = "Retorna la información de un cliente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado",
            content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerCliente(
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long id) {
        log.info("GET /api/v1/clientes/{} - Obtener cliente", id);
        return ResponseEntity.ok(clienteService.obtenerCliente(id));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        log.info("GET /api/v1/clientes - Listar clientes");
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<ClienteDTO>> listarClientesActivos() {
        log.info("GET /api/v1/clientes/activos - Listar clientes activos");
        return ResponseEntity.ok(clienteService.listarClientesActivos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteDTO>> buscarPorNombre(@RequestParam String nombre) {
        log.info("GET /api/v1/clientes/buscar?nombre={}", nombre);
        return ResponseEntity.ok(clienteService.buscarPorNombre(nombre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        log.info("PUT /api/v1/clientes/{} - Actualizar cliente", id);
        return ResponseEntity.ok(clienteService.actualizarCliente(id, clienteDTO));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarCliente(@PathVariable Long id) {
        log.info("PATCH /api/v1/clientes/{}/desactivar - Desactivar cliente", id);
        clienteService.desactivarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        log.info("DELETE /api/v1/clientes/{} - Eliminar cliente", id);
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}

