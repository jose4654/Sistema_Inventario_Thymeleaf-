package com.JOSE.Inventario.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "detalles_pedido")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cantidad de unidades de un producto en este detalle
    private Integer cantidad;

    // Precio del producto al momento de la compra
    private Double precioUnitario;

    // Relación muchos-a-uno con Producto: muchos detalles pueden referirse al mismo producto
    @ManyToOne
    @JoinColumn(name = "producto_id") // FK en la tabla detalle_pedido
    @JsonIgnore
    private Producto producto;

    // Relación muchos-a-uno con Pedido: muchos detalles pueden pertenecer al mismo pedido
    @ManyToOne
    @JoinColumn(name = "pedido_id") // FK en la tabla detalle_pedido
    @JsonIgnore
    private Pedido pedido;

    // Constructor vacío (obligatorio para JPA)
    public DetallePedido() {}

    // Constructor con todos los parámetros
    public DetallePedido(Integer cantidad, Double precioUnitario, Producto producto, Pedido pedido) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.producto = producto;
        this.pedido = pedido;
    }

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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Double getSubtotal() {
        return cantidad * precioUnitario;
    }

    // Método para mostrar el detalle en texto (útil para debugging)
    @Override
    public String toString() {
        return "DetallePedido{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", producto=" + (producto != null ? producto.getNombre() : "null") +
                '}';
    }
}
