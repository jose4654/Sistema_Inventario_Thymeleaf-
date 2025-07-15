package com.JOSE.Inventario.DTO;

public class DetalleRequesDto {
    private Integer cantidad;
    private Double precioUnitario;
    private Long productoId;  // Solo necesitamos el ID del producto

    // Getters y Setters
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
