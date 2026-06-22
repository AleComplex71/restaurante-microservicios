package com.restaurante.reportes.repository;

import com.restaurante.reportes.entity.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findByTipo(String tipo);
    List<Reporte> findByCompletadoTrue();
    List<Reporte> findByCompletadoFalse();
}

