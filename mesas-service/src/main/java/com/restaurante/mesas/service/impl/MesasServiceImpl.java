package com.restaurante.mesas.service.impl;

import com.restaurante.mesas.dto.MesaDTO;
import com.restaurante.mesas.dto.ReservaDTO;
import com.restaurante.mesas.entity.Mesa;
import com.restaurante.mesas.entity.Reserva;
import com.restaurante.mesas.exception.ResourceNotFoundException;
import com.restaurante.mesas.repository.MesaRepository;
import com.restaurante.mesas.repository.ReservaRepository;
import com.restaurante.mesas.service.MesasService;
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
public class MesasServiceImpl implements MesasService {

    private final MesaRepository mesaRepository;
    private final ReservaRepository reservaRepository;

    @Override
    public MesaDTO crearMesa(MesaDTO mesaDTO) {
        log.info("Creando mesa: {}", mesaDTO.getNumero());
        Mesa mesa = new Mesa();
        mesa.setNumero(mesaDTO.getNumero());
        mesa.setCapacidad(mesaDTO.getCapacidad());
        mesa.setUbicacion(mesaDTO.getUbicacion());
        mesa.setActiva(true);
        Mesa guardada = mesaRepository.save(mesa);
        log.info("Mesa creada: {}", guardada.getId());
        return convertirADTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public MesaDTO obtenerMesa(Long id) {
        Mesa mesa = mesaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada"));
        return convertirADTO(mesa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MesaDTO> listarMesas() {
        return mesaRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MesaDTO> obtenerMesasDisponibles() {
        return mesaRepository.findByEstado(Mesa.EstadoMesa.DISPONIBLE).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MesaDTO obtenerMesaPorCapacidad(Integer capacidad) {
        return mesaRepository.findByCapacidadGreaterThanEqual(capacidad).stream()
            .filter(m -> m.getEstado() == Mesa.EstadoMesa.DISPONIBLE)
            .map(this::convertirADTO)
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("No hay mesas disponibles con esa capacidad"));
    }

    @Override
    public MesaDTO cambiarEstadoMesa(Long id, String nuevoEstado) {
        Mesa mesa = mesaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada"));
        mesa.setEstado(Mesa.EstadoMesa.valueOf(nuevoEstado.toUpperCase()));
        Mesa actualizada = mesaRepository.save(mesa);
        log.info("Estado de mesa {} actualizado a: {}", id, nuevoEstado);
        return convertirADTO(actualizada);
    }

    @Override
    public ReservaDTO crearReserva(ReservaDTO reservaDTO) {
        log.info("Creando reserva para mesa: {}", reservaDTO.getMesaId());
        Reserva reserva = new Reserva();
        reserva.setMesaId(reservaDTO.getMesaId());
        reserva.setClienteId(reservaDTO.getClienteId());
        reserva.setFechaReserva(reservaDTO.getFechaReserva());
        reserva.setCantidadPersonas(reservaDTO.getCantidadPersonas());
        reserva.setNotas(reservaDTO.getNotas());
        Reserva guardada = reservaRepository.save(reserva);
        log.info("Reserva creada: {}", guardada.getId());
        return convertirADTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaDTO obtenerReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        return convertirADTO(reserva);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaDTO> listarReservas() {
        return reservaRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaDTO> obtenerReservasPorMesa(Long mesaId) {
        return reservaRepository.findByMesaId(mesaId).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public ReservaDTO actualizarEstadoReserva(Long id, String nuevoEstado) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        reserva.setEstado(Reserva.EstadoReserva.valueOf(nuevoEstado.toUpperCase()));
        Reserva actualizada = reservaRepository.save(reserva);
        log.info("Reserva {} actualizada a: {}", id, nuevoEstado);
        return convertirADTO(actualizada);
    }

    private MesaDTO convertirADTO(Mesa mesa) {
        return new MesaDTO(mesa.getId(), mesa.getNumero(), mesa.getCapacidad(), mesa.getEstado().toString(), mesa.getUbicacion(), mesa.getActiva());
    }

    private ReservaDTO convertirADTO(Reserva reserva) {
        return new ReservaDTO(reserva.getId(), reserva.getMesaId(), reserva.getClienteId(), reserva.getFechaReserva(),
            reserva.getCantidadPersonas(), reserva.getEstado().toString(), reserva.getNotas(), reserva.getFechaCreacion());
    }
}

