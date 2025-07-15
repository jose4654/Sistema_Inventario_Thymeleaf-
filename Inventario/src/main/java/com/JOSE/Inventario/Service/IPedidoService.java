package com.JOSE.Inventario.Service;

import java.util.List;
import java.util.Optional;
import com.JOSE.Inventario.DTO.PedidoRequesDteo;
import com.JOSE.Inventario.DTO.PedidoResponseDto;

public interface IPedidoService {
	public List<PedidoResponseDto> listaPedido();
	public Optional<PedidoResponseDto> buscarPedidoid(Long id);
	public List<PedidoResponseDto> listaPedidoIdUsuario(Long id);
	public PedidoResponseDto guardarPedido(PedidoRequesDteo pedidoDto);
	public void eliminarPedido(Long id);
	public PedidoResponseDto actualizarPedido(Long id, PedidoRequesDteo pedidoDto);
}
