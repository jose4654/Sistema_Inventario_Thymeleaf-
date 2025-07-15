package com.JOSE.Inventario.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JOSE.Inventario.DTO.PermissionResponseDto;
import com.JOSE.Inventario.DTO.RoleRequesDto;
import com.JOSE.Inventario.DTO.RoleResponseDto;
import com.JOSE.Inventario.Model.Permission;
import com.JOSE.Inventario.Model.Role;
import com.JOSE.Inventario.Repository.IPermissionRepository;
import com.JOSE.Inventario.Repository.IRoleRepository;

/**
 * Servicio que implementa la lógica de negocio para la gestión de roles.
 * Utiliza métodos de conversión separados para mantener el código organizado y reutilizable.
 */
@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IPermissionRepository permissionRepository;

    @Override
    public List<RoleResponseDto> listaRoles() {
        return roleRepository.findAll().stream()
            .map(this::convertirARoleResponseDto)
            .collect(Collectors.toList());
    }

    @Override
    public RoleResponseDto buscarRoleId(Long id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return convertirARoleResponseDto(role);
    }

    @Override
    public RoleResponseDto guardarRole(RoleRequesDto roleDto) {
        Role role = convertirARole(roleDto);
        Role roleGuardado = roleRepository.save(role);
        return convertirARoleResponseDto(roleGuardado);
    }

    @Override
    public void eliminarRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleResponseDto actualizarRole(Long id, RoleRequesDto roleDto) {
        Role roleExistente = roleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        
        actualizarRoleDesdeDto(roleExistente, roleDto);
        Role roleActualizado = roleRepository.save(roleExistente);
        return convertirARoleResponseDto(roleActualizado);
    }

    /**
     * Convierte una entidad Role a un DTO de respuesta.
     * Este método se utiliza en:
     * - listaRoles(): para convertir cada rol de la lista
     * - buscarRoleId(): para convertir el rol encontrado
     * - guardarRole(): para convertir el rol guardado
     * - actualizarRole(): para convertir el rol actualizado
     * 
     * Separar esta lógica permite:
     * - Reutilizar el código de conversión
     * - Mantener la consistencia en la conversión
     * - Facilitar cambios en la estructura del DTO
     */
    private RoleResponseDto convertirARoleResponseDto(Role role) {
        RoleResponseDto dto = new RoleResponseDto();
        dto.setId(role.getId());
        dto.setRole(role.getRole());
        
        // Convertir los permisos a DTOs (usando List para el DTO)
        List<PermissionResponseDto> permisosDto = role.getPermissionsList().stream()
            .map(this::convertirPermisoADto)
            .collect(Collectors.toList());
        dto.setPermissions(permisosDto);
        
        return dto;
    }

    /**
     * Convierte un DTO de solicitud a una entidad Role.
     * Este método se utiliza en:
     * - guardarRole(): para convertir el DTO antes de guardar
     * 
     * Separar esta lógica permite:
     * - Validar y transformar los datos antes de crear la entidad
     * - Mantener la lógica de creación de entidades en un solo lugar
     * - Facilitar cambios en la estructura de la entidad
     */
    private Role convertirARole(RoleRequesDto dto) {
        Role role = new Role();
        role.setRole(dto.getRole());
        
        // Establecer los permisos si se proporcionaron IDs (usando Set para la entidad)
        if (dto.getPermissionIds() != null && !dto.getPermissionIds().isEmpty()) {
            Set<Permission> permisos = dto.getPermissionIds().stream()
                .map(permissionId -> permissionRepository.findById(permissionId)
                    .orElseThrow(() -> new RuntimeException("Permiso no encontrado: " + permissionId)))
                .collect(Collectors.toSet());
            role.setPermissionsList(permisos);
        }
        
        return role;
    }

    /**
     * Actualiza una entidad Role existente con los datos del DTO.
     * Este método se utiliza en:
     * - actualizarRole(): para actualizar los campos del rol existente
     * 
     * Separar esta lógica permite:
     * - Mantener la lógica de actualización en un solo lugar
     * - Evitar duplicación de código
     * - Facilitar cambios en la lógica de actualización
     */
    private void actualizarRoleDesdeDto(Role role, RoleRequesDto dto) {
        role.setRole(dto.getRole());
        
        // Actualizar los permisos si se proporcionaron IDs (usando Set para la entidad)
        if (dto.getPermissionIds() != null && !dto.getPermissionIds().isEmpty()) {
            Set<Permission> permisos = dto.getPermissionIds().stream()
                .map(permissionId -> permissionRepository.findById(permissionId)
                    .orElseThrow(() -> new RuntimeException("Permiso no encontrado: " + permissionId)))
                .collect(Collectors.toSet());
            role.setPermissionsList(permisos);
        }
    }

    /**
     * Convierte una entidad Permission a un DTO de respuesta.
     * Este método es utilizado internamente para convertir los permisos de un rol.
     */
    private PermissionResponseDto convertirPermisoADto(Permission permission) {
        PermissionResponseDto dto = new PermissionResponseDto();
        dto.setId(permission.getId());
        dto.setPermissionName(permission.getPermissionName());
        return dto;
    }
}

