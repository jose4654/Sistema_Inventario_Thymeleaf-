package com.JOSE.Inventario.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JOSE.Inventario.Model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {
    // Devuelve todos los pedidos de un usuario según su id
   
}
