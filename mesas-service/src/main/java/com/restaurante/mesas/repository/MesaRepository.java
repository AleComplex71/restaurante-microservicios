package com.restaurante.mesas.repository;

import com.restaurante.mesas.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    List<Mesa> findByEstado(Mesa.EstadoMesa estado);
    List<Mesa> findByActivaTrue();
    List<Mesa> findByCapacidadGreaterThanEqual(Integer capacidad);
}

