package com.JOSE.Inventario.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JOSE.Inventario.Model.Categoria;
@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

}

