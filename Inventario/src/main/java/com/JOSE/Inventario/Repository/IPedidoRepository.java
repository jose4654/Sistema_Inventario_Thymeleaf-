package com.JOSE.Inventario.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JOSE.Inventario.Model.Pedido;
@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long> {
	 List<Pedido> findByUsuarioId(Long usuarioId);

}
