package com.restaurante.inventario.service.impl;

import com.restaurante.inventario.dto.IngredienteDTO;
import com.restaurante.inventario.entity.Ingrediente;
import com.restaurante.inventario.exception.ResourceNotFoundException;
import com.restaurante.inventario.repository.IngredienteRepository;
import com.restaurante.inventario.service.InventarioService;
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
public class InventarioServiceImpl implements InventarioService {

    private final IngredienteRepository ingredienteRepository;

    @Override
    public IngredienteDTO agregarIngrediente(IngredienteDTO ingredienteDTO) {
        log.info("Agregando ingrediente: {}", ingredienteDTO.getNombre());
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre(ingredienteDTO.getNombre());
        ingrediente.setDescripcion(ingredienteDTO.getDescripcion());
        ingrediente.setCantidad(ingredienteDTO.getCantidad());
        ingrediente.setUnidad(ingredienteDTO.getUnidad());
        ingrediente.setCosto(ingredienteDTO.getCosto());
        ingrediente.setStockMinimo(ingredienteDTO.getStockMinimo());
        ingrediente.setCategoria(ingredienteDTO.getCategoria());
        ingrediente.setDisponible(true);

        Ingrediente guardado = ingredienteRepository.save(ingrediente);
        log.info("Ingrediente creado: {}", guardado.getId());
        return convertirADTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public IngredienteDTO obtenerIngrediente(Long id) {
        log.debug("Obteniendo ingrediente: {}", id);
        Ingrediente ingrediente = ingredienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ingrediente no encontrado"));
        return convertirADTO(ingrediente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredienteDTO> listarIngredientes() {
        log.info("Listando ingredientes");
        return ingredienteRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredienteDTO> listarDisponibles() {
        log.info("Listando ingredientes disponibles");
        return ingredienteRepository.findByDisponibleTrue().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredienteDTO> buscarPorCategoria(String categoria) {
        log.info("Buscando ingredientes en categoría: {}", categoria);
        return ingredienteRepository.findByCategoria(categoria).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredienteDTO> obtenerBajoStock() {
        log.info("Obteniendo ingredientes con bajo stock");
        return ingredienteRepository.findAll().stream()
            .filter(i -> i.getCantidad() <= i.getStockMinimo())
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    @Override
    public IngredienteDTO actualizarIngrediente(Long id, IngredienteDTO ingredienteDTO) {
        log.info("Actualizando ingrediente: {}", id);
        Ingrediente ingrediente = ingredienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ingrediente no encontrado"));
        
        ingrediente.setNombre(ingredienteDTO.getNombre());
        ingrediente.setDescripcion(ingredienteDTO.getDescripcion());
        ingrediente.setUnidad(ingredienteDTO.getUnidad());
        ingrediente.setCosto(ingredienteDTO.getCosto());
        ingrediente.setStockMinimo(ingredienteDTO.getStockMinimo());
        ingrediente.setCategoria(ingredienteDTO.getCategoria());

        Ingrediente actualizado = ingredienteRepository.save(ingrediente);
        log.info("Ingrediente actualizado: {}", id);
        return convertirADTO(actualizado);
    }

    @Override
    public void descontarStock(Long id, Double cantidad) {
        log.info("Descontando {} unidades del ingrediente {}", cantidad, id);
        Ingrediente ingrediente = ingredienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ingrediente no encontrado"));
        
        ingrediente.setCantidad(ingrediente.getCantidad() - cantidad);
        ingredienteRepository.save(ingrediente);
        log.info("Stock descontado: {}", id);
    }

    @Override
    public void aumentarStock(Long id, Double cantidad) {
        log.info("Aumentando {} unidades al ingrediente {}", cantidad, id);
        Ingrediente ingrediente = ingredienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ingrediente no encontrado"));
        
        ingrediente.setCantidad(ingrediente.getCantidad() + cantidad);
        ingredienteRepository.save(ingrediente);
        log.info("Stock aumentado: {}", id);
    }

    @Override
    public void eliminarIngrediente(Long id) {
        log.info("Eliminando ingrediente: {}", id);
        if (!ingredienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ingrediente no encontrado");
        }
        ingredienteRepository.deleteById(id);
        log.info("Ingrediente eliminado: {}", id);
    }

    private IngredienteDTO convertirADTO(Ingrediente ingrediente) {
        return new IngredienteDTO(ingrediente.getId(), ingrediente.getNombre(), ingrediente.getDescripcion(),
            ingrediente.getCantidad(), ingrediente.getUnidad(), ingrediente.getCosto(),
            ingrediente.getStockMinimo(), ingrediente.getDisponible(), ingrediente.getCategoria());
    }
}

