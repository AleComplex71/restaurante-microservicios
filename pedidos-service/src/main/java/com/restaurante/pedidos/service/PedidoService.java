package com.restaurante.pedidos.service;

import com.restaurante.pedidos.dto.PedidoDTO;
import java.util.List;

public interface PedidoService {
    PedidoDTO crearPedido(PedidoDTO pedidoDTO);
    PedidoDTO obtenerPedido(Long id);
    List<PedidoDTO> listarPedidos();
    List<PedidoDTO> obtenerPedidosPorCliente(Long clienteId);
    List<PedidoDTO> obtenerPedidosPorEstado(String estado);
    PedidoDTO actualizarEstadoPedido(Long id, String nuevoEstado);
    void cancelarPedido(Long id);
    void eliminarPedido(Long id);
}

