package com.restaurante.empleados.service;

import com.restaurante.empleados.dto.EmpleadoDTO;
import java.util.List;

public interface EmpleadoService {
    EmpleadoDTO crearEmpleado(EmpleadoDTO empleadoDTO);
    EmpleadoDTO obtenerEmpleado(Long id);
    List<EmpleadoDTO> listarEmpleados();
    List<EmpleadoDTO> listarEmpleadosActivos();
    List<EmpleadoDTO> listarPorPuesto(String puesto);
    EmpleadoDTO actualizarEmpleado(Long id, EmpleadoDTO empleadoDTO);
    void desactivarEmpleado(Long id);
    void eliminarEmpleado(Long id);
}

