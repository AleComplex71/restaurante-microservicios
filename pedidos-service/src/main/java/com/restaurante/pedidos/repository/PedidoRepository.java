package com.restaurante.pedidos.repository;

import com.restaurante.pedidos.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByEstado(Pedido.EstadoPedido estado);
    List<Pedido> findByClienteIdAndEstado(Long clienteId, Pedido.EstadoPedido estado);
}

