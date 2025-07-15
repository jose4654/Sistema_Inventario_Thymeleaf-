package com.JOSE.Inventario.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JOSE.Inventario.DTO.ProductoRequestDto;
import com.JOSE.Inventario.DTO.ProductoResponseDto;
import com.JOSE.Inventario.DTO.CategoriaResponseDto;
import com.JOSE.Inventario.Model.Producto;
import com.JOSE.Inventario.Model.Categoria;
import com.JOSE.Inventario.Repository.IProductoRepository;
import com.JOSE.Inventario.Repository.ICategoriaRepository;

/**
 * Servicio que implementa la lógica de negocio para la gestión de productos.
 * Utiliza métodos de conversión separados para mantener el código organizado y reutilizable.
 */
@Service
public class ProductoService implements IProductoService {

	@Autowired
	private IProductoRepository productoRepository;

	@Autowired
	private ICategoriaRepository categoriaRepository;

	@Override
	public List<ProductoResponseDto> listaProductos() {
		return productoRepository.findAll().stream()
			.map(this::convertirAProductoResponseDto)
			.collect(Collectors.toList());
	}

	@Override
	public ProductoResponseDto buscarProductoId(Long id) {
		Producto producto = productoRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Producto no encontrado"));
		return convertirAProductoResponseDto(producto);
	}

	@Override
	public ProductoResponseDto guardarProducto(ProductoRequestDto productoDto) {
		Producto producto = convertirAProducto(productoDto);
		Producto productoGuardado = productoRepository.save(producto);
		return convertirAProductoResponseDto(productoGuardado);
	}

	@Override
	public void eliminarProductoPorId(Long id) {
		productoRepository.deleteById(id);
	}

	@Override
	public ProductoResponseDto actualizarProducto(Long id, ProductoRequestDto productoDto) {
		Producto productoExistente = productoRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Producto no encontrado"));
		
		actualizarProductoDesdeDto(productoExistente, productoDto);
		Producto productoActualizado = productoRepository.save(productoExistente);
		return convertirAProductoResponseDto(productoActualizado);
	}

	/**
	 * Convierte una entidad Producto a un DTO de respuesta.
	 * Este método se utiliza en:
	 * - listaProductos(): para convertir cada producto de la lista
	 * - buscarProductoId(): para convertir el producto encontrado
	 * - guardarProducto(): para convertir el producto guardado
	 * - actualizarProducto(): para convertir el producto actualizado
	 * 
	 * Separar esta lógica permite:
	 * - Reutilizar el código de conversión
	 * - Mantener la consistencia en la conversión
	 * - Facilitar cambios en la estructura del DTO
	 * - Controlar qué información se expone al cliente
	 */
	private ProductoResponseDto convertirAProductoResponseDto(Producto producto) {
		ProductoResponseDto dto = new ProductoResponseDto();
		dto.setId(producto.getId());
		dto.setNombre(producto.getNombre());
		dto.setPrecio(producto.getPrecio());
		dto.setExistencia(producto.getExistencia());
		dto.setImagen(producto.getImagen());
		dto.setEstado(producto.isEstado());
		
		// Convertir la categoría a DTO si existe
		if (producto.getCategoria() != null) {
			CategoriaResponseDto categoriaDto = new CategoriaResponseDto();
			categoriaDto.setId(producto.getCategoria().getId());
			categoriaDto.setNombre(producto.getCategoria().getNombre());
			categoriaDto.setDescripcion(producto.getCategoria().getDescripcion());
			dto.setCategoria(categoriaDto);
		}
		
		return dto;
	}

	/**
	 * Convierte un DTO de solicitud a una entidad Producto.
	 * Este método se utiliza en:
	 * - guardarProducto(): para convertir el DTO antes de guardar
	 * 
	 * Separar esta lógica permite:
	 * - Validar y transformar los datos antes de crear la entidad
	 * - Mantener la lógica de creación de entidades en un solo lugar
	 * - Facilitar cambios en la estructura de la entidad
	 * - Aplicar reglas de negocio durante la conversión
	 */
	private Producto convertirAProducto(ProductoRequestDto dto) {
		Producto producto = new Producto();
		producto.setNombre(dto.getNombre());
		producto.setPrecio(dto.getPrecio());
		producto.setExistencia(dto.getExistencia());
		producto.setImagen(dto.getImagen());
		producto.setEstado(dto.isEstado());
		
		// Establecer la categoría si se proporcionó un ID
		if (dto.getCategoriaId() != null) {
			Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
				.orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + dto.getCategoriaId()));
			producto.setCategoria(categoria);
		}
		
		return producto;
	}

	/**
	 * Actualiza una entidad Producto existente con los datos del DTO.
	 * Este método se utiliza en:
	 * - actualizarProducto(): para actualizar los campos del producto existente
	 * 
	 * Separar esta lógica permite:
	 * - Mantener la lógica de actualización en un solo lugar
	 * - Evitar duplicación de código
	 * - Facilitar cambios en la lógica de actualización
	 * - Aplicar reglas de negocio durante la actualización
	 */
	private void actualizarProductoDesdeDto(Producto producto, ProductoRequestDto dto) {
		producto.setNombre(dto.getNombre());
		producto.setPrecio(dto.getPrecio());
		producto.setExistencia(dto.getExistencia());
		producto.setImagen(dto.getImagen());
		producto.setEstado(dto.isEstado());
		
		// Actualizar la categoría si se proporcionó un ID
		if (dto.getCategoriaId() != null) {
			Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
				.orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + dto.getCategoriaId()));
			producto.setCategoria(categoria);
		}
	}
}
