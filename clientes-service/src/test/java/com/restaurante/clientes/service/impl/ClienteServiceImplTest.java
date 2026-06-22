package com.restaurante.clientes.service.impl;

import com.restaurante.clientes.dto.ClienteDTO;
import com.restaurante.clientes.entity.Cliente;
import com.restaurante.clientes.exception.ResourceNotFoundException;
import com.restaurante.clientes.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteService = new ClienteServiceImpl(clienteRepository);
    }

    @Test
    void crearCliente_Success() {
        ClienteDTO dto = new ClienteDTO(null, "Juan Perez", "juan@example.com", "12345", "Calle Falsa 123", "DNI123", true, 0, 0.0, null, null);

        when(clienteRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());

        ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
        Cliente saved = new Cliente();
        saved.setId(1L);
        saved.setNombre(dto.getNombre());
        saved.setEmail(dto.getEmail());
        saved.setTelefono(dto.getTelefono());
        saved.setDireccion(dto.getDireccion());
        saved.setDocumento(dto.getDocumento());
        saved.setActivo(true);

        when(clienteRepository.save(any(Cliente.class))).thenReturn(saved);

        ClienteDTO result = clienteService.crearCliente(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(dto.getEmail(), result.getEmail());

        verify(clienteRepository).findByEmail(dto.getEmail());
        verify(clienteRepository).save(captor.capture());

        Cliente persisted = captor.getValue();
        assertEquals(dto.getNombre(), persisted.getNombre());
    }

    @Test
    void crearCliente_DuplicateEmail_ThrowsRuntimeException() {
        ClienteDTO dto = new ClienteDTO(null, "Ana", "ana@example.com", "123", "Dir", "DOC", true, 0, 0.0, null, null);
        Cliente existing = new Cliente();
        existing.setId(2L);
        existing.setEmail(dto.getEmail());

        when(clienteRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(existing));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> clienteService.crearCliente(dto));
        assertTrue(ex.getMessage().contains("email"));

        verify(clienteRepository).findByEmail(dto.getEmail());
        verify(clienteRepository, never()).save(any());
    }
}

