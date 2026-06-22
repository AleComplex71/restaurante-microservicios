package com.restaurante.pedidos.service.impl;

import com.restaurante.pedidos.client.PlatosClient;
import com.restaurante.pedidos.dto.DetallePedidoDTO;
import com.restaurante.pedidos.dto.PedidoDTO;
import com.restaurante.pedidos.dto.PlatoDTO;
import com.restaurante.pedidos.entity.DetallePedido;
import com.restaurante.pedidos.entity.Pedido;
import com.restaurante.pedidos.exception.ResourceNotFoundException;
import com.restaurante.pedidos.repository.PedidoRepository;
import com.restaurante.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PlatosClient platosClient;

    @Override
    public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
        log.info("Creando nuevo pedido para cliente: {}", pedidoDTO.getClienteId());

        // Validar platos en el pedido
        for (DetallePedidoDTO detalle : pedidoDTO.getDetalles()) {
            try {
                platosClient.validarPlato(detalle.getPlatoId());
                PlatoDTO plato = platosClient.obtenerPlato(detalle.getPlatoId());
                detalle.setPlatoNombre(plato.getNombre());
                detalle.setPrecioUnitario(plato.getPrecio());
                detalle.setSubtotal(plato.getPrecio().multiply(BigDecimal.valueOf(detalle.getCantidad())));
            } catch (Exception e) {
                log.error("Error al validar plato {}: {}", detalle.getPlatoId(), e.getMessage());
                throw new ResourceNotFoundException("Error al validar plato: " + e.getMessage());
            }
        }

        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setClienteId(pedidoDTO.getClienteId());
        pedido.setEstado(Pedido.EstadoPedido.PENDIENTE);
        pedido.setNotas(pedidoDTO.getNotas());

        // Crear detalles y calcular total
        BigDecimal total = BigDecimal.ZERO;
        List<DetallePedido> detalles = pedidoDTO.getDetalles().stream()
            .map(detalle -> {
                DetallePedido d = new DetallePedido();
                d.setPlatoId(detalle.getPlatoId());
                d.setPlatoNombre(detalle.getPlatoNombre());
                d.setCantidad(detalle.getCantidad());
                d.setPrecioUnitario(detalle.getPrecioUnitario());
                d.setNotas(detalle.getNotas());
                d.calcularSubtotal();
                return d;
            })
            .collect(Collectors.toList());

        total = detalles.stream()
            .map(DetallePedido::getSubtotal)
            .filter(s -> s != null)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setDetalles(detalles);
        pedido.setTotal(total);

        Pedido guardado = pedidoRepository.save(pedido);
        log.info("Pedido creado exitosamente con ID: {}", guardado.getId());

        return convertirADTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoDTO obtenerPedido(Long id) {
        log.debug("Buscando pedido con ID: {}", id);
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Pedido no encontrado con ID: {}", id);
                return new ResourceNotFoundException("Pedido no encontrado con ID: " + id);
            });
        return convertirADTO(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> listarPedidos() {
        log.info("Listando todos los pedidos");
        return pedidoRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> obtenerPedidosPorCliente(Long clienteId) {
        log.info("Listando pedidos del cliente: {}", clienteId);
        return pedidoRepository.findByClienteId(clienteId)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> obtenerPedidosPorEstado(String estado) {
        log.info("Listando pedidos con estado: {}", estado);
        try {
            Pedido.EstadoPedido estadoEnum = Pedido.EstadoPedido.valueOf(estado.toUpperCase());
            return pedidoRepository.findByEstado(estadoEnum)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            log.error("Estado inválido: {}", estado);
            throw new ResourceNotFoundException("Estado de pedido inválido: " + estado);
        }
    }

    @Override
    public PedidoDTO actualizarEstadoPedido(Long id, String nuevoEstado) {
        log.info("Actualizando estado del pedido {} a: {}", id, nuevoEstado);

        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));

        try {
            Pedido.EstadoPedido estado = Pedido.EstadoPedido.valueOf(nuevoEstado.toUpperCase());
            pedido.setEstado(estado);
            Pedido actualizado = pedidoRepository.save(pedido);
            log.info("Estado del pedido {} actualizado a: {}", id, nuevoEstado);
            return convertirADTO(actualizado);
        } catch (IllegalArgumentException e) {
            log.error("Estado inválido: {}", nuevoEstado);
            throw new ResourceNotFoundException("Estado de pedido inválido: " + nuevoEstado);
        }
    }

    @Override
    public void cancelarPedido(Long id) {
        log.info("Cancelando pedido con ID: {}", id);
        PedidoDTO pedidoDTO = actualizarEstadoPedido(id, "CANCELADO");
        log.info("Pedido cancelado: {}", id);
    }

    @Override
    public void eliminarPedido(Long id) {
        log.info("Eliminando pedido con ID: {}", id);
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido no encontrado");
        }
        pedidoRepository.deleteById(id);
        log.info("Pedido eliminado: {}", id);
    }

    private PedidoDTO convertirADTO(Pedido pedido) {
        List<DetallePedidoDTO> detalles = pedido.getDetalles().stream()
            .map(d -> new DetallePedidoDTO(
                d.getId(),
                d.getPlatoId(),
                d.getPlatoNombre(),
                d.getCantidad(),
                d.getPrecioUnitario(),
                d.getSubtotal(),
                d.getNotas()
            ))
            .collect(Collectors.toList());

        return new PedidoDTO(
            pedido.getId(),
            pedido.getClienteId(),
            pedido.getFechaCreacion(),
            pedido.getEstado().toString(),
            pedido.getTotal(),
            detalles,
            pedido.getNotas(),
            pedido.getUltimaActualizacion()
        );
    }
}

