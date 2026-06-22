package com.restaurante.notificaciones.service;

import com.restaurante.notificaciones.dto.NotificacionDTO;
import java.util.List;

public interface NotificacionService {
    NotificacionDTO crearNotificacion(NotificacionDTO notificacionDTO);
    NotificacionDTO obtenerNotificacion(Long id);
    List<NotificacionDTO> listarNotificaciones();
    List<NotificacionDTO> obtenerNotificacionesPorUsuario(Long usuarioId);
    List<NotificacionDTO> obtenerNoLeidas(Long usuarioId);
    void marcarComoLeida(Long id);
    void marcarComoEnviada(Long id);
    void eliminarNotificacion(Long id);
}

