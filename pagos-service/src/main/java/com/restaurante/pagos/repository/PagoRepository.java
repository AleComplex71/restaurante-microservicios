package com.restaurante.pagos.repository;

import com.restaurante.pagos.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByPedidoId(Long pedidoId);
    List<Pago> findByClienteId(Long clienteId);
    List<Pago> findByEstado(Pago.EstadoPago estado);
}

