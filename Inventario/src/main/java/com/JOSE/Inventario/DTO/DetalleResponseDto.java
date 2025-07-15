package com.JOSE.Inventario.DTO;

public class DetalleResponseDto {
    private Long id;
    private Integer cantidad;
    private Double precioUnitario;
    private ProductoResponseDto producto;  // Incluimos el producto completo

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

    public ProductoResponseDto getProducto() {
        return producto;
    }

    public void setProducto(ProductoResponseDto producto) {
        this.producto = producto;
    }
}
