package com.restaurante.pagos.service;

import com.restaurante.pagos.dto.PagoDTO;
import java.util.List;

public interface PagoService {
    PagoDTO procesarPago(PagoDTO pagoDTO);
    PagoDTO obtenerPago(Long id);
    List<PagoDTO> listarPagos();
    List<PagoDTO> obtenerPagosPorPedido(Long pedidoId);
    List<PagoDTO> obtenerPagosPorCliente(Long clienteId);
    PagoDTO reembolsarPago(Long id);
    void cancelarPago(Long id);
}

