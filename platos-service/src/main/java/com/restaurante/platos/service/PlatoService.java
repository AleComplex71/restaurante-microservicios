package com.restaurante.platos.service;

import com.restaurante.platos.dto.PlatoDTO;
import java.util.List;

public interface PlatoService {
    PlatoDTO crearPlato(PlatoDTO platoDTO);
    PlatoDTO obtenerPlato(Long id);
    List<PlatoDTO> listarPlatos();
    List<PlatoDTO> listarPlatosDisponibles();
    List<PlatoDTO> buscarPorCategoria(String categoria);
    List<PlatoDTO> buscarPorNombre(String nombre);
    PlatoDTO actualizarPlato(Long id, PlatoDTO platoDTO);
    void eliminarPlato(Long id);
    void cambiarDisponibilidad(Long id, Boolean disponible);
}

