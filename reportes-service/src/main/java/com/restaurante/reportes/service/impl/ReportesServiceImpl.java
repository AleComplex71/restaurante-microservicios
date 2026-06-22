package com.restaurante.reportes.service.impl;

import com.restaurante.reportes.dto.ReporteDTO;
import com.restaurante.reportes.entity.Reporte;
import com.restaurante.reportes.exception.ResourceNotFoundException;
import com.restaurante.reportes.repository.ReporteRepository;
import com.restaurante.reportes.service.ReportesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReportesServiceImpl implements ReportesService {

    private final ReporteRepository reporteRepository;

    @Override
    public ReporteDTO crearReporte(ReporteDTO reporteDTO) {
        log.info("Creando reporte: {}", reporteDTO.getTitulo());
        Reporte reporte = new Reporte();
        reporte.setTipo(reporteDTO.getTipo());
        reporte.setTitulo(reporteDTO.getTitulo());
        reporte.setDescripcion(reporteDTO.getDescripcion());
        reporte.setContenido(reporteDTO.getContenido());
        reporte.setFormato(reporteDTO.getFormato());
        Reporte guardado = reporteRepository.save(reporte);
        log.info("Reporte creado: {}", guardado.getId());
        return convertirADTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ReporteDTO obtenerReporte(Long id) {
        Reporte reporte = reporteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado"));
        return convertirADTO(reporte);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteDTO> listarReportes() {
        return reporteRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteDTO> obtenerPorTipo(String tipo) {
        return reporteRepository.findByTipo(tipo).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteDTO> obtenerCompletados() {
        return reporteRepository.findByCompletadoTrue().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerEstadísticasGenerales() {
        log.info("Obteniendo estadísticas generales");
        Map<String, Object> stats = new HashMap<>();
        List<Reporte> reportes = reporteRepository.findAll();
        stats.put("totalReportes", reportes.size());
        stats.put("reportesCompletados", reportes.stream().filter(Reporte::getCompletado).count());
        stats.put("totalVentas", reportes.stream().mapToDouble(r -> r.getTotalVentas() != null ? r.getTotalVentas().doubleValue() : 0.0).sum());
        stats.put("promedioPedidos", reportes.stream().mapToInt(r -> r.getCantidadPedidos() != null ? r.getCantidadPedidos() : 0).average().orElse(0));
        return stats;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerEstadísticasPorPeriodo(String periodo) {
        log.info("Obteniendo estadísticas por período: {}", periodo);
        Map<String, Object> stats = new HashMap<>();
        stats.put("periodo", periodo);
        stats.put("datos", "Estadísticas para período: " + periodo);
        return stats;
    }

    @Override
    public ReporteDTO actualizarReporte(Long id, ReporteDTO reporteDTO) {
        Reporte reporte = reporteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado"));
        reporte.setTipo(reporteDTO.getTipo());
        reporte.setTitulo(reporteDTO.getTitulo());
        reporte.setDescripcion(reporteDTO.getDescripcion());
        reporte.setContenido(reporteDTO.getContenido());
        reporte.setTotalVentas(reporteDTO.getTotalVentas());
        reporte.setCantidadPedidos(reporteDTO.getCantidadPedidos());
        reporte.setPromedioPedido(reporteDTO.getPromedioPedido());
        Reporte actualizado = reporteRepository.save(reporte);
        log.info("Reporte actualizado: {}", id);
        return convertirADTO(actualizado);
    }

    @Override
    public void marcarComoCompletado(Long id) {
        log.info("Marcando reporte como completado: {}", id);
        Reporte reporte = reporteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado"));
        reporte.setCompletado(true);
        reporteRepository.save(reporte);
    }

    @Override
    public void eliminarReporte(Long id) {
        if (!reporteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reporte no encontrado");
        }
        reporteRepository.deleteById(id);
        log.info("Reporte eliminado: {}", id);
    }

    private ReporteDTO convertirADTO(Reporte reporte) {
        return new ReporteDTO(reporte.getId(), reporte.getTipo(), reporte.getTitulo(), reporte.getDescripcion(),
            reporte.getContenido(), reporte.getFechaGeneracion(), reporte.getFormato(), reporte.getTotalVentas(),
            reporte.getCantidadPedidos(), reporte.getPromedioPedido(), reporte.getCompletado());
    }
}

