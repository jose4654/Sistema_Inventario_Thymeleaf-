package com.JOSE.Inventario.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JOSE.Inventario.DTO.PedidoRequesDteo;
import com.JOSE.Inventario.DTO.PedidoResponseDto;
import com.JOSE.Inventario.DTO.UserSecResponseDto;
import com.JOSE.Inventario.DTO.DetallePedidoResponseDto;
import com.JOSE.Inventario.DTO.ProductoResponseDto;
import com.JOSE.Inventario.DTO.DetallePedidoRequestDto;
import com.JOSE.Inventario.Model.Pedido;
import com.JOSE.Inventario.Model.UserSec;
import com.JOSE.Inventario.Model.DetallePedido;
import com.JOSE.Inventario.Model.Producto;
import com.JOSE.Inventario.Repository.IPedidoRepository;
import com.JOSE.Inventario.Repository.IUserRepository;
import com.JOSE.Inventario.Repository.IProductoRepository;

/**
 * Servicio que implementa la lógica de negocio para la gestión de pedidos.
 * Utiliza métodos de conversión separados para mantener el código organizado y reutilizable.
 */
@Service
public class PedidoService implements IPedidoService {

    @Autowired
    private IPedidoRepository pedidoRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Override
    public List<PedidoResponseDto> listaPedido() {
        return pedidoRepository.findAll().stream()
            .map(this::convertirAPedidoResponseDto)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<PedidoResponseDto> buscarPedidoid(Long id) {
        return pedidoRepository.findById(id)
            .map(this::convertirAPedidoResponseDto);
    }

    @Override
    public PedidoResponseDto guardarPedido(PedidoRequesDteo pedidoDto) {
        Pedido pedido = convertirAPedido(pedidoDto);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return convertirAPedidoResponseDto(pedidoGuardado);
    }
	@Override
	public List<PedidoResponseDto> listaPedidoIdUsuario(Long id) {
		List<Pedido> pedidos = pedidoRepository.findByUsuarioId(id);
		 List<PedidoResponseDto> pedidosRespon = new ArrayList<>();
		   for (Pedido pedido : pedidos) {
		        PedidoResponseDto dto = convertirAPedidoResponseDto(pedido);
		        pedidosRespon.add(dto); // ✅ agregás cada uno a la lista
		    }
		return pedidosRespon;
	}

    @Override
    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public PedidoResponseDto actualizarPedido(Long id, PedidoRequesDteo pedidoDto) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        // Solo actualizamos el estado
        pedidoExistente.setEstado(pedidoDto.isEstado());
        
        Pedido pedidoActualizado = pedidoRepository.save(pedidoExistente);
        return convertirAPedidoResponseDto(pedidoActualizado);
    }

    /**
     * Convierte una entidad Pedido a un DTO de respuesta.
     * Este método se utiliza en:
     * - listaPedido(): para convertir cada pedido de la lista
     * - buscarPedidoid(): para convertir el pedido encontrado
     * - guardarPedido(): para convertir el pedido guardado
     * - actualizarPedido(): para convertir el pedido actualizado
     * 
     * Separar esta lógica permite:
     * - Reutilizar el código de conversión
     * - Mantener la consistencia en la conversión
     * - Facilitar cambios en la estructura del DTO
     * - Controlar qué información se expone al cliente
     * - Manejar relaciones complejas (detalles del pedido)
     */
    private PedidoResponseDto convertirAPedidoResponseDto(Pedido pedido) {
        PedidoResponseDto dto = new PedidoResponseDto();
        dto.setId(pedido.getId());
        dto.setEstado(pedido.isEstado());
        dto.setTotal(pedido.getTotal());
        dto.setFecha(pedido.getFecha());
        
        // Convertir el usuario a DTO si existe
        if (pedido.getUsuario() != null) {
            UserSecResponseDto usuarioDto = new UserSecResponseDto();
            usuarioDto.setId(pedido.getUsuario().getId());
            usuarioDto.setUserName(pedido.getUsuario().getUserName());
            usuarioDto.setEnable(pedido.getUsuario().isEnable());
            usuarioDto.setAccountNotExpired(pedido.getUsuario().isAccountNotExpired());
            usuarioDto.setAccountNotLocked(pedido.getUsuario().isAccountNotLocked());
            usuarioDto.setCredentialNotExpired(pedido.getUsuario().isCredentialNotExpired());
            dto.setUsuario(usuarioDto);
        }
        
        // Convertir los detalles del pedido a DTOs si existen
        if (pedido.getDetalles() != null && !pedido.getDetalles().isEmpty()) {
            List<DetallePedidoResponseDto> detallesDto = pedido.getDetalles().stream()
                .map(detalle -> {
                    DetallePedidoResponseDto detalleDto = new DetallePedidoResponseDto();
                    detalleDto.setId(detalle.getId());                   
                    detalleDto.setCantidad(detalle.getCantidad());
                    detalleDto.setPrecioUnitario(detalle.getPrecioUnitario());
                    detalleDto.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
                    
                    // Convertir el producto del detalle si existe
                    if (detalle.getProducto() != null) {
                        ProductoResponseDto productoDto = new ProductoResponseDto();
                        productoDto.setId(detalle.getProducto().getId());
                        productoDto.setNombre(detalle.getProducto().getNombre());
                        productoDto.setPrecio(detalle.getProducto().getPrecio());
                        productoDto.setExistencia(detalle.getProducto().getExistencia());
                        productoDto.setEstado(detalle.getProducto().isEstado());
                        detalleDto.setProducto(productoDto);
                    }
                    
                    return detalleDto;
                })
                .collect(Collectors.toList());
            dto.setDetalles(detallesDto);
        }
        
        return dto;
    }

    /**
     * Convierte un DTO de solicitud a una entidad Pedido.
     * Este método se utiliza en:
     * - guardarPedido(): para convertir el DTO antes de guardar
     * 
     * Separar esta lógica permite:
     * - Validar y transformar los datos antes de crear la entidad
     * - Mantener la lógica de creación de entidades en un solo lugar
     * - Facilitar cambios en la estructura de la entidad
     * - Aplicar reglas de negocio durante la conversión
     * - Manejar la creación de relaciones (detalles del pedido)
     */
    private Pedido convertirAPedido(PedidoRequesDteo dto) {
        Pedido pedido = new Pedido();
        pedido.setEstado(dto.isEstado());
        pedido.setTotal(dto.getTotal());
        
        // Establecer el usuario si se proporcionó un ID
        if (dto.getIdUsuario() != null) {
            UserSec usuario = userRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + dto.getIdUsuario()));
            pedido.setUsuario(usuario);
        }
        
        // Establecer los detalles del pedido si se proporcionaron
        if (dto.getDetalles() != null && !dto.getDetalles().isEmpty()) {
            Set<DetallePedido> detalles = dto.getDetalles().stream()
                .map(detalleDto -> {
                    DetallePedido detalle = new DetallePedido();
                    detalle.setCantidad(detalleDto.getCantidad());
                    detalle.setPrecioUnitario(detalleDto.getPrecioUnitario());
                    
                    // Establecer el producto del detalle si se proporcionó un ID
                    if (detalleDto.getProductoId() != null) {
                        Producto producto = productoRepository.findById(detalleDto.getProductoId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + detalleDto.getProductoId()));
                        detalle.setProducto(producto);
                    }
                    
                    detalle.setPedido(pedido);
                    return detalle;
                })
                .collect(Collectors.toSet());
            pedido.setDetalles(detalles);
        }
        
        return pedido;
    }

    /**
     * Actualiza una entidad Pedido existente con los datos del DTO.
     * Este método se utiliza en:
     * - actualizarPedido(): para actualizar los campos del pedido existente
     * 
     * Separar esta lógica permite:
     * - Mantener la lógica de actualización en un solo lugar
     * - Evitar duplicación de código
     * - Facilitar cambios en la lógica de actualización
     * - Aplicar reglas de negocio durante la actualización
     * - Manejar la actualización de relaciones (detalles del pedido)
     */
    private void actualizarPedidoDesdeDto(Pedido pedido, PedidoRequesDteo dto) {
        pedido.setEstado(dto.isEstado());
        pedido.setTotal(dto.getTotal());
        
        // Actualizar el usuario si se proporcionó un ID
        if (dto.getIdUsuario() != null) {
            UserSec usuario = userRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + dto.getIdUsuario()));
            pedido.setUsuario(usuario);
        }
        
        // Actualizar los detalles del pedido si se proporcionaron
        if (dto.getDetalles() != null && !dto.getDetalles().isEmpty()) {
            // Primero eliminamos los detalles existentes
            pedido.getDetalles().clear();
            
            // Luego agregamos los nuevos detalles
            Set<DetallePedido> detalles = dto.getDetalles().stream()
                .map(detalleDto -> {
                    DetallePedido detalle = new DetallePedido();
                    detalle.setCantidad(detalleDto.getCantidad());
                    detalle.setPrecioUnitario(detalleDto.getPrecioUnitario());
                    
                    if (detalleDto.getProductoId() != null) {
                        Producto producto = productoRepository.findById(detalleDto.getProductoId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + detalleDto.getProductoId()));
                        detalle.setProducto(producto);
                    }
                    
                    detalle.setPedido(pedido);
                    return detalle;
                })
                .collect(Collectors.toSet());
            pedido.setDetalles(detalles);
        }
    }


}






