package com.JOSE.Inventario.DTO;

public class ProductoRequestDto {
    private Long id;
    private String nombre;
    private Double precio;
    private Integer existencia;
    private String imagen;
    private boolean estado;
    private Long categoriaId;  // Solo necesitamos el ID de la categoría
    private CategoriaRequestDto categoria;  // Para soportar el formato JSON del cliente

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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getExistencia() {
        return existencia;
    }

    public void setExistencia(Integer existencia) {
        this.existencia = existencia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public CategoriaRequestDto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaRequestDto categoria) {
        this.categoria = categoria;
    }

    // Clase interna para representar la categoría en el JSON
    public static class CategoriaRequestDto {
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
