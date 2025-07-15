package com.JOSE.Inventario.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoRequesDteo {
    private Long id;
    private Long idUsuario;
    private boolean estado = true;
    private Double total;
    private LocalDateTime fecha = LocalDateTime.now();
    private List<DetallePedidoRequestDto> detalles;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<DetallePedidoRequestDto> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoRequestDto> detalles) {
        this.detalles = detalles;
    }

    // Clase interna para los detalles del pedido
    public static class DetallePedidoRequestDto {
        private Long id;
        private Integer cantidad;
        private Double precioUnitario;
        private Long productoId;

        // Getters y Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public Double getPrecioUnitario() {
            return precioUnitario;
        }

        public void setPrecioUnitario(Double precioUnitario) {
            this.precioUnitario = precioUnitario;
        }

        public Long getProductoId() {
            return productoId;
        }

        public void setProductoId(Long productoId) {
            this.productoId = productoId;
        }
    }
}
