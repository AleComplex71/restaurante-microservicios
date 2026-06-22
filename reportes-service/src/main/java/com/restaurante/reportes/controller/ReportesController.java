package com.restaurante.reportes.controller;

import com.restaurante.reportes.dto.ReporteDTO;
import com.restaurante.reportes.service.ReportesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
@Slf4j
public class ReportesController {

    private final ReportesService reportesService;

    @PostMapping
    public ResponseEntity<ReporteDTO> crearReporte(@Valid @RequestBody ReporteDTO reporteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reportesService.crearReporte(reporteDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteDTO> obtenerReporte(@PathVariable Long id) {
        return ResponseEntity.ok(reportesService.obtenerReporte(id));
    }

    @GetMapping
    public ResponseEntity<List<ReporteDTO>> listarReportes() {
        return ResponseEntity.ok(reportesService.listarReportes());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(reportesService.obtenerPorTipo(tipo));
    }

    @GetMapping("/completados")
    public ResponseEntity<List<ReporteDTO>> obtenerCompletados() {
        return ResponseEntity.ok(reportesService.obtenerCompletados());
    }

    @GetMapping("/estadisticas/generales")
    public ResponseEntity<Map<String, Object>> obtenerEstadísticasGenerales() {
        return ResponseEntity.ok(reportesService.obtenerEstadísticasGenerales());
    }

    @GetMapping("/estadisticas/periodo")
    public ResponseEntity<Map<String, Object>> obtenerEstadísticasPorPeriodo(@RequestParam String periodo) {
        return ResponseEntity.ok(reportesService.obtenerEstadísticasPorPeriodo(periodo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteDTO> actualizarReporte(@PathVariable Long id, @Valid @RequestBody ReporteDTO reporteDTO) {
        return ResponseEntity.ok(reportesService.actualizarReporte(id, reporteDTO));
    }

    @PatchMapping("/{id}/completar")
    public ResponseEntity<Void> marcarComoCompletado(@PathVariable Long id) {
        reportesService.marcarComoCompletado(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Long id) {
        reportesService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }
}

