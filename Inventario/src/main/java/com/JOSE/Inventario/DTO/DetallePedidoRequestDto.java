package com.JOSE.Inventario.DTO;

import lombok.Data;

@Data
public class DetallePedidoRequestDto {
    private Long id;
    private Integer cantidad;
    private Double precioUnitario;
    private Long productoId;
} 