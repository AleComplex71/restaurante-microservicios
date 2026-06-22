package com.restaurante.pedidos.client;

import com.restaurante.pedidos.dto.PlatoDTO;
import com.restaurante.pedidos.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlatosClient {

    private final WebClient webClient;

    public PlatoDTO obtenerPlato(Long platoId) {
        log.debug("Consultando plato con ID: {} en servicio de platos", platoId);
        try {
            return webClient.get()
                .uri("/api/v1/platos/{id}", platoId)
                .retrieve()
                .bodyToMono(PlatoDTO.class)
                .timeout(Duration.ofSeconds(5))
                .onErrorResume(ex -> {
                    log.error("Error al consultar plato {}: {}", platoId, ex.getMessage());
                    return Mono.error(new ResourceNotFoundException("No se pudo validar el plato con ID: " + platoId));
                })
                .block();
        } catch (Exception e) {
            log.error("Excepción al consultar plato: {}", e.getMessage());
            throw new ResourceNotFoundException("Error al validar plato: " + e.getMessage());
        }
    }

    public void validarPlato(Long platoId) {
        log.debug("Validando disponibilidad del plato: {}", platoId);
        PlatoDTO plato = obtenerPlato(platoId);
        
        if (plato == null) {
            log.error("Plato no existe: {}", platoId);
            throw new ResourceNotFoundException("El plato con ID " + platoId + " no existe");
        }

        if (!plato.getDisponible()) {
            log.warn("Plato no disponible: {}", platoId);
            throw new ResourceNotFoundException("El plato " + plato.getNombre() + " no está disponible");
        }

        log.debug("Plato validado correctamente: {}", platoId);
    }
}


