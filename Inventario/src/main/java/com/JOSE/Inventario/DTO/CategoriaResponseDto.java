package com.JOSE.Inventario.DTO;

import java.util.List;

public class CategoriaResponseDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private boolean estado;
    private List<ProductoResponseDto> productos;  // Lista de productos en esta categoría

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<ProductoResponseDto> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoResponseDto> productos) {
        this.productos = productos;
    }
}
