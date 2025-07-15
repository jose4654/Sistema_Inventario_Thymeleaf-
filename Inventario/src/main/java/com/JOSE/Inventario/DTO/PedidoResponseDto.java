package com.JOSE.Inventario.DTO;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class PedidoResponseDto {
    private Long id;
    private UserSecResponseDto usuario;
    private boolean estado;
    private LocalDateTime fecha;
    private Double total;
    private List<DetallePedidoResponseDto> detalles;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserSecResponseDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UserSecResponseDto usuario) {
        this.usuario = usuario;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<DetallePedidoResponseDto> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoResponseDto> detalles) {
        this.detalles = detalles;
    }
}
