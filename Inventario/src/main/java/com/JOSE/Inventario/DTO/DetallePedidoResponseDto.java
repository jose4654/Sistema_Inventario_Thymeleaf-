package com.JOSE.Inventario.DTO;

import lombok.Data;


public class DetallePedidoResponseDto {
    private Long id;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
    private ProductoResponseDto producto;

    // Getter y Setter para id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter y Setter para cantidad
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    // Getter y Setter para precioUnitario
    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    // Getter y Setter para subtotal
    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    // Getter y Setter para producto
    public ProductoResponseDto getProducto() {
        return producto;
    }

    public void setProducto(ProductoResponseDto producto) {
        this.producto = producto;
    }
}
