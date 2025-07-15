package com.JOSE.Inventario.DTO;

import java.util.List;

public class RoleResponseDto {
    private Long id;
    private String role;
    private List<PermissionResponseDto> permissions;  // Lista de permisos del rol

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<PermissionResponseDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionResponseDto> permissions) {
        this.permissions = permissions;
    }
}
