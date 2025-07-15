package com.JOSE.Inventario.Model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String role;

    // Usamos Set porque no permite repetidos
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "roles_permissions",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissionsList = new HashSet<>();  // Asegúrate de usar tu clase Permission aquí

    // Constructor vacío
    public Role() {
    }

    // Constructor con todos los campos
    public Role(long id, String role, Set<Permission> permissionsList) {
        this.id = id;
        this.role = role;
        this.permissionsList = permissionsList;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Permission> getPermissionsList() {
        return permissionsList;
    }

    public void setPermissionsList(Set<Permission> permissionsList) {
        this.permissionsList = permissionsList;
    }
}
