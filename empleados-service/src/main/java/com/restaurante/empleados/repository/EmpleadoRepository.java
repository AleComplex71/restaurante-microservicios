package com.restaurante.empleados.repository;

import com.restaurante.empleados.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByEmail(String email);
    List<Empleado> findByActivoTrue();
    List<Empleado> findByPuesto(String puesto);
}

