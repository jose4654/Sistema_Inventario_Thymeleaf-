package com.JOSE.Inventario.Service;

import java.util.List;
import com.JOSE.Inventario.DTO.ProductoRequestDto;
import com.JOSE.Inventario.DTO.ProductoResponseDto;

public interface IProductoService {
	public List<ProductoResponseDto> listaProductos();
	public ProductoResponseDto buscarProductoId(Long id);
	public ProductoResponseDto guardarProducto(ProductoRequestDto productoDto);
	public void eliminarProductoPorId(Long id);
	public ProductoResponseDto actualizarProducto(Long id, ProductoRequestDto productoDto);
}
