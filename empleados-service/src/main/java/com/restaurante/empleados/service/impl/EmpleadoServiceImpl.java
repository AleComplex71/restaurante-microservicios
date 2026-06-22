package com.restaurante.empleados.service.impl;

import com.restaurante.empleados.dto.EmpleadoDTO;
import com.restaurante.empleados.entity.Empleado;
import com.restaurante.empleados.exception.ResourceNotFoundException;
import com.restaurante.empleados.repository.EmpleadoRepository;
import com.restaurante.empleados.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    @Override
    public EmpleadoDTO crearEmpleado(EmpleadoDTO empleadoDTO) {
        log.info("Creando empleado: {}", empleadoDTO.getEmail());
        Empleado empleado = new Empleado();
        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setEmail(empleadoDTO.getEmail());
        empleado.setTelefono(empleadoDTO.getTelefono());
        empleado.setPuesto(empleadoDTO.getPuesto());
        empleado.setSalario(empleadoDTO.getSalario());
        empleado.setTipoContrato(Empleado.TipoContrato.valueOf(empleadoDTO.getTipoContrato()));
        empleado.setFechaIngreso(empleadoDTO.getFechaIngreso());
        Empleado guardado = empleadoRepository.save(empleado);
        log.info("Empleado creado: {}", guardado.getId());
        return convertirADTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public EmpleadoDTO obtenerEmpleado(Long id) {
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado"));
        return convertirADTO(empleado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoDTO> listarEmpleados() {
        return empleadoRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoDTO> listarEmpleadosActivos() {
        return empleadoRepository.findByActivoTrue().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoDTO> listarPorPuesto(String puesto) {
        return empleadoRepository.findByPuesto(puesto).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public EmpleadoDTO actualizarEmpleado(Long id, EmpleadoDTO empleadoDTO) {
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado"));
        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setTelefono(empleadoDTO.getTelefono());
        empleado.setPuesto(empleadoDTO.getPuesto());
        empleado.setSalario(empleadoDTO.getSalario());
        Empleado actualizado = empleadoRepository.save(empleado);
        log.info("Empleado actualizado: {}", id);
        return convertirADTO(actualizado);
    }

    @Override
    public void desactivarEmpleado(Long id) {
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado"));
        empleado.setActivo(false);
        empleadoRepository.save(empleado);
        log.info("Empleado desactivado: {}", id);
    }

    @Override
    public void eliminarEmpleado(Long id) {
        if (!empleadoRepository.existsById(id)) throw new ResourceNotFoundException("Empleado no encontrado");
        empleadoRepository.deleteById(id);
        log.info("Empleado eliminado: {}", id);
    }

    private EmpleadoDTO convertirADTO(Empleado empleado) {
        return new EmpleadoDTO(empleado.getId(), empleado.getNombre(), empleado.getEmail(), empleado.getTelefono(),
            empleado.getPuesto(), empleado.getSalario(), empleado.getTipoContrato().toString(),
            empleado.getFechaIngreso(), empleado.getActivo());
    }
}

