package com.restaurante.clientes.service;

import com.restaurante.clientes.dto.ClienteDTO;
import java.util.List;

public interface ClienteService {
    ClienteDTO crearCliente(ClienteDTO clienteDTO);
    ClienteDTO obtenerCliente(Long id);
    List<ClienteDTO> listarClientes();
    List<ClienteDTO> listarClientesActivos();
    List<ClienteDTO> buscarPorNombre(String nombre);
    ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO);
    void desactivarCliente(Long id);
    void eliminarCliente(Long id);
}

