package com.restaurante.pagos.service.impl;

import com.restaurante.pagos.dto.PagoDTO;
import com.restaurante.pagos.entity.Pago;
import com.restaurante.pagos.exception.ResourceNotFoundException;
import com.restaurante.pagos.repository.PagoRepository;
import com.restaurante.pagos.service.PagoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;

    @Override
    public PagoDTO procesarPago(PagoDTO pagoDTO) {
        log.info("Procesando pago de ${} para pedido {}", pagoDTO.getMonto(), pagoDTO.getPedidoId());

        Pago pago = new Pago();
        pago.setPedidoId(pagoDTO.getPedidoId());
        pago.setClienteId(pagoDTO.getClienteId());
        pago.setMonto(pagoDTO.getMonto());
        pago.setMetodoPago(Pago.MetodoPago.valueOf(pagoDTO.getMetodoPago().toUpperCase()));
        pago.setEstado(Pago.EstadoPago.COMPLETED);
        pago.setNumeroTransaccion(UUID.randomUUID().toString());
        pago.setFechaPago(LocalDateTime.now());
        pago.setReferencia(pagoDTO.getReferencia());

        Pago guardado = pagoRepository.save(pago);
        log.info("Pago procesado exitosamente con ID: {}", guardado.getId());

        return convertirADTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PagoDTO obtenerPago(Long id) {
        log.debug("Obteniendo pago con ID: {}", id);
        Pago pago = pagoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));
        return convertirADTO(pago);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoDTO> listarPagos() {
        log.info("Listando todos los pagos");
        return pagoRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoDTO> obtenerPagosPorPedido(Long pedidoId) {
        log.info("Obteniendo pagos del pedido: {}", pedidoId);
        return pagoRepository.findByPedidoId(pedidoId).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoDTO> obtenerPagosPorCliente(Long clienteId) {
        log.info("Obteniendo pagos del cliente: {}", clienteId);
        return pagoRepository.findByClienteId(clienteId).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public PagoDTO reembolsarPago(Long id) {
        log.info("Reembolsando pago con ID: {}", id);
        Pago pago = pagoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));
        pago.setEstado(Pago.EstadoPago.REFUNDED);
        Pago actualizado = pagoRepository.save(pago);
        log.info("Pago reembolsado: {}", id);
        return convertirADTO(actualizado);
    }

    @Override
    public void cancelarPago(Long id) {
        log.info("Cancelando pago con ID: {}", id);
        Pago pago = pagoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));
        pago.setEstado(Pago.EstadoPago.CANCELLED);
        pagoRepository.save(pago);
        log.info("Pago cancelado: {}", id);
    }

    private PagoDTO convertirADTO(Pago pago) {
        return new PagoDTO(pago.getId(), pago.getPedidoId(), pago.getClienteId(), pago.getMonto(),
            pago.getMetodoPago().toString(), pago.getEstado().toString(), pago.getNumeroTransaccion(),
            pago.getFechaPago(), pago.getReferencia());
    }
}

