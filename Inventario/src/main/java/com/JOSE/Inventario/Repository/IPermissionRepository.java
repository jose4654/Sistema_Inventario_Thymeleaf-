package com.JOSE.Inventario.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JOSE.Inventario.Model.Permission;


@Repository
public interface IPermissionRepository extends JpaRepository<Permission, Long> {
}


