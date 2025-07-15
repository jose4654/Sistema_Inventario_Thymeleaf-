package com.JOSE.Inventario.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.JOSE.Inventario.Model.UserSec;
import com.JOSE.Inventario.Repository.IUserRepository;

//Indica que esta clase es un servicio de Spring
//Se encarga de cargar los detalles del usuario durante la autenticación
@Service
public class UserDetailsServiceImp implements UserDetailsService {

 // Inyecta el repositorio de usuarios para acceder a la base de datos
 @Autowired
 private IUserRepository userRepo;

 // Método obligatorio de la interfaz UserDetailsService
 // Se ejecuta durante el proceso de autenticación
 @Override
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     UserSec userSec = userRepo.findUserEntityByUserName(username)
             .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no fue encontrado"));

     List<GrantedAuthority> authorityList = new ArrayList<>();

     userSec.getRolesList().forEach(role -> {
         authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole())));
     });

     
     userSec.getRolesList().stream()
             .flatMap(role -> role.getPermissionsList().stream())
             .forEach(permission -> {
                 authorityList.add(new SimpleGrantedAuthority(permission.getPermissionName()));
             });

     return new User(
         userSec.getUserName(),
         userSec.getPassword(),
         userSec.isEnable(),
         userSec.isAccountNotExpired(),
         userSec.isCredentialNotExpired(),
         userSec.isAccountNotLocked(),
         authorityList
     );
 }
}

