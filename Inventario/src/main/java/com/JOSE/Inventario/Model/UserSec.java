package com.JOSE.Inventario.Model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

//Anotación que marca esta clase como una entidad JPA (persistente en la base de datos)
@Entity
//Define el nombre de la tabla en la base de datos asociada a esta entidad
@Table(name = "users")
public class UserSec {

 // Define la clave primaria de la tabla
 @Id
 // Indica que el valor del ID se genera automáticamente (autoincremental)
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 // Define la columna y especifica que debe ser única (no puede haber dos usuarios con el mismo nombre)
 @Column(unique = true)
 private String userName;

 // Contraseña del usuario
 private String password;

 // Si la cuenta está habilitada o no
 private boolean enable;

 // Indica si la cuenta aún no expiró
 private boolean accountNotExpired;

 // Indica si la cuenta está bloqueada o no
 private boolean accountNotLocked;

 // Indica si las credenciales están vigentes o expiradas
 private boolean credentialNotExpired;

 // Relación muchos a muchos entre usuarios y roles
 // fetch = EAGER significa que los roles se cargarán automáticamente junto con el usuario
 // cascade = ALL permite que las operaciones (guardar, borrar, etc.) se propaguen a los roles relacionados
 @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
 // Define la tabla intermedia "user_roles" que une users y roles
 @JoinTable(
     name = "user_roles", // nombre de la tabla intermedia
     joinColumns = @JoinColumn(name = "user_id"), // columna que referencia al usuario
     inverseJoinColumns = @JoinColumn(name = "role_id") // columna que referencia al rol
 )
 private Set<Role> rolesList = new HashSet<>();

 // Constructor vacío
 public UserSec() {
 }

 // Constructor con todos los atributos
 public UserSec(Long id, String userName, String password, boolean enable,
                boolean accountNotExpired, boolean accountNotLocked,
                boolean credentialNotExpired, Set<Role> rolesList) {
     this.id = id;
     this.userName = userName;
     this.password = password;
     this.enable = enable;
     this.accountNotExpired = accountNotExpired;
     this.accountNotLocked = accountNotLocked;
     this.credentialNotExpired = credentialNotExpired;
     this.rolesList = rolesList;
 }


    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isAccountNotExpired() {
        return accountNotExpired;
    }

    public void setAccountNotExpired(boolean accountNotExpired) {
        this.accountNotExpired = accountNotExpired;
    }

    public boolean isAccountNotLocked() {
        return accountNotLocked;
    }

    public void setAccountNotLocked(boolean accountNotLocked) {
        this.accountNotLocked = accountNotLocked;
    }

    public boolean isCredentialNotExpired() {
        return credentialNotExpired;
    }

    public void setCredentialNotExpired(boolean credentialNotExpired) {
        this.credentialNotExpired = credentialNotExpired;
    }

    public Set<Role> getRolesList() {
        return rolesList;
    }

    public void setRolesList(Set<Role> rolesList) {
        this.rolesList = rolesList;
    }
}
