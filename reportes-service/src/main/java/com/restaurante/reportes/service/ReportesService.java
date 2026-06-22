package com.restaurante.reportes.service;

import com.restaurante.reportes.dto.ReporteDTO;
import java.util.List;
import java.util.Map;

public interface ReportesService {
    ReporteDTO crearReporte(ReporteDTO reporteDTO);
    ReporteDTO obtenerReporte(Long id);
    List<ReporteDTO> listarReportes();
    List<ReporteDTO> obtenerPorTipo(String tipo);
    List<ReporteDTO> obtenerCompletados();
    Map<String, Object> obtenerEstadísticasGenerales();
    Map<String, Object> obtenerEstadísticasPorPeriodo(String periodo);
    ReporteDTO actualizarReporte(Long id, ReporteDTO reporteDTO);
    void marcarComoCompletado(Long id);
    void eliminarReporte(Long id);
}

