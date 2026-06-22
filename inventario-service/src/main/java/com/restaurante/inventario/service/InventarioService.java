package com.restaurante.inventario.service;

import com.restaurante.inventario.dto.IngredienteDTO;
import java.util.List;

public interface InventarioService {
    IngredienteDTO agregarIngrediente(IngredienteDTO ingredienteDTO);
    IngredienteDTO obtenerIngrediente(Long id);
    List<IngredienteDTO> listarIngredientes();
    List<IngredienteDTO> listarDisponibles();
    List<IngredienteDTO> buscarPorCategoria(String categoria);
    List<IngredienteDTO> obtenerBajoStock();
    IngredienteDTO actualizarIngrediente(Long id, IngredienteDTO ingredienteDTO);
    void descontarStock(Long id, Double cantidad);
    void aumentarStock(Long id, Double cantidad);
    void eliminarIngrediente(Long id);
}

