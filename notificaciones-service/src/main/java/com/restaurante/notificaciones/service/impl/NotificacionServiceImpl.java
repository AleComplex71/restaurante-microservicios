package com.restaurante.notificaciones.service.impl;

import com.restaurante.notificaciones.dto.NotificacionDTO;
import com.restaurante.notificaciones.entity.Notificacion;
import com.restaurante.notificaciones.exception.ResourceNotFoundException;
import com.restaurante.notificaciones.repository.NotificacionRepository;
import com.restaurante.notificaciones.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;

    @Override
    public NotificacionDTO crearNotificacion(NotificacionDTO notificacionDTO) {
        log.info("Creando notificación para usuario: {}", notificacionDTO.getUsuarioId());
        Notificacion notificacion = new Notificacion();
        notificacion.setUsuarioId(notificacionDTO.getUsuarioId());
        notificacion.setTipo(notificacionDTO.getTipo());
        notificacion.setAsunto(notificacionDTO.getAsunto());
        notificacion.setMensaje(notificacionDTO.getMensaje());
        Notificacion guardada = notificacionRepository.save(notificacion);
        log.info("Notificación creada: {}", guardada.getId());
        return convertirADTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificacionDTO obtenerNotificacion(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada"));
        return convertirADTO(notificacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionDTO> listarNotificaciones() {
        return notificacionRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionDTO> obtenerNotificacionesPorUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioId(usuarioId).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionDTO> obtenerNoLeidas(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdAndLeidaFalse(usuarioId).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public void marcarComoLeida(Long id) {
        log.info("Marcando notificación como leída: {}", id);
        Notificacion notificacion = notificacionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada"));
        notificacion.setLeida(true);
        notificacion.setFechaLectura(LocalDateTime.now());
        notificacionRepository.save(notificacion);
    }

    @Override
    public void marcarComoEnviada(Long id) {
        log.info("Marcando notificación como enviada: {}", id);
        Notificacion notificacion = notificacionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada"));
        notificacion.setEnviada(true);
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacionRepository.save(notificacion);
    }

    @Override
    public void eliminarNotificacion(Long id) {
        if (!notificacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notificación no encontrada");
        }
        notificacionRepository.deleteById(id);
        log.info("Notificación eliminada: {}", id);
    }

    private NotificacionDTO convertirADTO(Notificacion notificacion) {
        return new NotificacionDTO(notificacion.getId(), notificacion.getUsuarioId(), notificacion.getTipo(),
            notificacion.getAsunto(), notificacion.getMensaje(), notificacion.getLeida(), notificacion.getEnviada(),
            notificacion.getFechaCreacion(), notificacion.getFechaEnvio(), notificacion.getFechaLectura());
    }
}

