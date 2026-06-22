package com.restaurante.platos.service.impl;

import com.restaurante.platos.dto.PlatoDTO;
import com.restaurante.platos.entity.Plato;
import com.restaurante.platos.exception.ResourceNotFoundException;
import com.restaurante.platos.repository.PlatoRepository;
import com.restaurante.platos.service.PlatoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PlatoServiceImpl implements PlatoService {

    private final PlatoRepository platoRepository;

    @Override
    public PlatoDTO crearPlato(PlatoDTO platoDTO) {
        log.info("Creando nuevo plato: {}", platoDTO.getNombre());
        
        Plato plato = new Plato();
        plato.setNombre(platoDTO.getNombre());
        plato.setDescripcion(platoDTO.getDescripcion());
        plato.setPrecio(platoDTO.getPrecio());
        plato.setCategoria(platoDTO.getCategoria());
        plato.setDisponible(true);
        plato.setTiempoPreparacion(platoDTO.getTiempoPreparacion());

        Plato guardado = platoRepository.save(plato);
        log.info("Plato creado exitosamente con ID: {}", guardado.getId());
        
        return convertirADTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PlatoDTO obtenerPlato(Long id) {
        log.debug("Buscando plato con ID: {}", id);
        
        Plato plato = platoRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Plato no encontrado con ID: {}", id);
                return new ResourceNotFoundException("Plato no encontrado con ID: " + id);
            });
        
        return convertirADTO(plato);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlatoDTO> listarPlatos() {
        log.info("Listando todos los platos");
        return platoRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlatoDTO> listarPlatosDisponibles() {
        log.info("Listando platos disponibles");
        return platoRepository.findByDisponibleTrue()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlatoDTO> buscarPorCategoria(String categoria) {
        log.info("Buscando platos por categoría: {}", categoria);
        return platoRepository.findByCategoria(categoria)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlatoDTO> buscarPorNombre(String nombre) {
        log.info("Buscando platos por nombre: {}", nombre);
        return platoRepository.findByNombreContainingIgnoreCase(nombre)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    @Override
    public PlatoDTO actualizarPlato(Long id, PlatoDTO platoDTO) {
        log.info("Actualizando plato con ID: {}", id);
        
        Plato plato = platoRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Intento de actualizar plato inexistente con ID: {}", id);
                return new ResourceNotFoundException("Plato no encontrado");
            });

        plato.setNombre(platoDTO.getNombre());
        plato.setDescripcion(platoDTO.getDescripcion());
        plato.setPrecio(platoDTO.getPrecio());
        plato.setCategoria(platoDTO.getCategoria());
        plato.setDisponible(platoDTO.getDisponible());
        plato.setTiempoPreparacion(platoDTO.getTiempoPreparacion());

        Plato actualizado = platoRepository.save(plato);
        log.info("Plato actualizado exitosamente: {}", id);
        
        return convertirADTO(actualizado);
    }

    @Override
    public void eliminarPlato(Long id) {
        log.info("Eliminando plato con ID: {}", id);
        
        if (!platoRepository.existsById(id)) {
            log.error("Intento de eliminar plato inexistente con ID: {}", id);
            throw new ResourceNotFoundException("Plato no encontrado");
        }
        
        platoRepository.deleteById(id);
        log.info("Plato eliminado exitosamente: {}", id);
    }

    @Override
    public void cambiarDisponibilidad(Long id, Boolean disponible) {
        log.info("Cambiando disponibilidad del plato {} a: {}", id, disponible);
        
        Plato plato = platoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Plato no encontrado"));
        
        plato.setDisponible(disponible);
        platoRepository.save(plato);
        
        log.info("Disponibilidad del plato {} actualizada a: {}", id, disponible);
    }

    private PlatoDTO convertirADTO(Plato plato) {
        return new PlatoDTO(
            plato.getId(),
            plato.getNombre(),
            plato.getDescripcion(),
            plato.getPrecio(),
            plato.getCategoria(),
            plato.getDisponible(),
            plato.getTiempoPreparacion()
        );
    }
}

