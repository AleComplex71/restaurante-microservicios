package com.restaurante.clientes.service.impl;

import com.restaurante.clientes.dto.ClienteDTO;
import com.restaurante.clientes.entity.Cliente;
import com.restaurante.clientes.exception.ResourceNotFoundException;
import com.restaurante.clientes.repository.ClienteRepository;
import com.restaurante.clientes.service.ClienteService;
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
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        log.info("Creando nuevo cliente: {}", clienteDTO.getEmail());

        if (clienteRepository.findByEmail(clienteDTO.getEmail()).isPresent()) {
            log.warn("Intento de crear cliente con email duplicado: {}", clienteDTO.getEmail());
            throw new RuntimeException("El email ya está registrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setDocumento(clienteDTO.getDocumento());
        cliente.setActivo(true);

        Cliente guardado = clienteRepository.save(cliente);
        log.info("Cliente creado exitosamente con ID: {}", guardado.getId());

        return convertirADTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO obtenerCliente(Long id) {
        log.debug("Buscando cliente con ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Cliente no encontrado con ID: {}", id);
                return new ResourceNotFoundException("Cliente no encontrado con ID: " + id);
            });
        return convertirADTO(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> listarClientes() {
        log.info("Listando todos los clientes");
        return clienteRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> listarClientesActivos() {
        log.info("Listando clientes activos");
        return clienteRepository.findByActivoTrue()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> buscarPorNombre(String nombre) {
        log.info("Buscando clientes por nombre: {}", nombre);
        return clienteRepository.findByNombreContainingIgnoreCase(nombre)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO) {
        log.info("Actualizando cliente con ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        cliente.setNombre(clienteDTO.getNombre());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setDocumento(clienteDTO.getDocumento());

        Cliente actualizado = clienteRepository.save(cliente);
        log.info("Cliente actualizado: {}", id);

        return convertirADTO(actualizado);
    }

    @Override
    public void desactivarCliente(Long id) {
        log.info("Desactivando cliente con ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        cliente.setActivo(false);
        clienteRepository.save(cliente);
        log.info("Cliente desactivado: {}", id);
    }

    @Override
    public void eliminarCliente(Long id) {
        log.info("Eliminando cliente con ID: {}", id);

        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }

        clienteRepository.deleteById(id);
        log.info("Cliente eliminado: {}", id);
    }

    private ClienteDTO convertirADTO(Cliente cliente) {
        return new ClienteDTO(
            cliente.getId(),
            cliente.getNombre(),
            cliente.getEmail(),
            cliente.getTelefono(),
            cliente.getDireccion(),
            cliente.getDocumento(),
            cliente.getActivo(),
            cliente.getTotalPedidos(),
            cliente.getGastTotal(),
            cliente.getFechaRegistro(),
            cliente.getUltimaCompra()
        );
    }
}

