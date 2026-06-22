package com.restaurante.inventario.repository;

import com.restaurante.inventario.entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    List<Ingrediente> findByDisponibleTrue();
    List<Ingrediente> findByCategoria(String categoria);
    List<Ingrediente> findByNombreContainingIgnoreCase(String nombre);
    List<Ingrediente> findByCantidadLessThanEqual(Double cantidad);
}

