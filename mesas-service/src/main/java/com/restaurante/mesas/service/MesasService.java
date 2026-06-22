package com.restaurante.mesas.service;

import com.restaurante.mesas.dto.MesaDTO;
import com.restaurante.mesas.dto.ReservaDTO;
import java.util.List;

public interface MesasService {
    MesaDTO crearMesa(MesaDTO mesaDTO);
    MesaDTO obtenerMesa(Long id);
    List<MesaDTO> listarMesas();
    List<MesaDTO> obtenerMesasDisponibles();
    MesaDTO obtenerMesaPorCapacidad(Integer capacidad);
    MesaDTO cambiarEstadoMesa(Long id, String nuevoEstado);
    ReservaDTO crearReserva(ReservaDTO reservaDTO);
    ReservaDTO obtenerReserva(Long id);
    List<ReservaDTO> listarReservas();
    List<ReservaDTO> obtenerReservasPorMesa(Long mesaId);
    ReservaDTO actualizarEstadoReserva(Long id, String nuevoEstado);
}

