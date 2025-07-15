package com.JOSE.Inventario.DTO;

import java.util.List;

public class RoleRequesDto {
    private String role;
    private List<Long> permissionIds;  // Lista de IDs de permisos

    // Getters y Setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
