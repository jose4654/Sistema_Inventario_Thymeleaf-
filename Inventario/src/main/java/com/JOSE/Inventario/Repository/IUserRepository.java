package com.JOSE.Inventario.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JOSE.Inventario.Model.UserSec;


@Repository
public interface IUserRepository extends JpaRepository<UserSec, Long> {
    // Método para buscar un usuario por su nombre de usuario
	Optional<UserSec> findUserEntityByUserName(String userName);

}
