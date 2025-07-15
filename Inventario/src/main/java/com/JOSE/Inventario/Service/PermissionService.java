package com.JOSE.Inventario.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JOSE.Inventario.DTO.PermissionRequesDto;
import com.JOSE.Inventario.DTO.PermissionResponseDto;
import com.JOSE.Inventario.Model.Permission;
import com.JOSE.Inventario.Repository.IPermissionRepository;

/**
 * Servicio que implementa la lógica de negocio para la gestión de permisos.
 * Utiliza métodos de conversión separados para mantener el código organizado y reutilizable.
 */
@Service
public class PermissionService implements IPermissionService {

    @Autowired
    private IPermissionRepository permissionRepository;

    @Override
    public List<PermissionResponseDto> listaPermisos() {
        return permissionRepository.findAll().stream()
            .map(this::convertirAPermissionResponseDto)
            .collect(Collectors.toList());
    }

    @Override
    public PermissionResponseDto buscarPermisoId(Long id) {
        Permission permission = permissionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
        return convertirAPermissionResponseDto(permission);
    }

    @Override
    public PermissionResponseDto guardarPermiso(PermissionRequesDto permisoDto) {
        Permission permission = convertirAPermission(permisoDto);
        Permission permissionGuardado = permissionRepository.save(permission);
        return convertirAPermissionResponseDto(permissionGuardado);
    }

    @Override
    public void eliminarPermiso(Long id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public PermissionResponseDto actualizarPermiso(Long id, PermissionRequesDto permisoDto) {
        Permission permissionExistente = permissionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
        
        actualizarPermissionDesdeDto(permissionExistente, permisoDto);
        Permission permissionActualizado = permissionRepository.save(permissionExistente);
        return convertirAPermissionResponseDto(permissionActualizado);
    }

    /**
     * Convierte una entidad Permission a un DTO de respuesta.
     * Este método se utiliza en:
     * - listaPermisos(): para convertir cada permiso de la lista
     * - buscarPermisoId(): para convertir el permiso encontrado
     * - guardarPermiso(): para convertir el permiso guardado
     * - actualizarPermiso(): para convertir el permiso actualizado
     * 
     * Separar esta lógica permite:
     * - Reutilizar el código de conversión
     * - Mantener la consistencia en la conversión
     * - Facilitar cambios en la estructura del DTO
     */
    private PermissionResponseDto convertirAPermissionResponseDto(Permission permission) {
        PermissionResponseDto dto = new PermissionResponseDto();
        dto.setId(permission.getId());
        dto.setPermissionName(permission.getPermissionName());
        return dto;
    }

    /**
     * Convierte un DTO de solicitud a una entidad Permission.
     * Este método se utiliza en:
     * - guardarPermiso(): para convertir el DTO antes de guardar
     * 
     * Separar esta lógica permite:
     * - Validar y transformar los datos antes de crear la entidad
     * - Mantener la lógica de creación de entidades en un solo lugar
     * - Facilitar cambios en la estructura de la entidad
     */
    private Permission convertirAPermission(PermissionRequesDto dto) {
        Permission permission = new Permission();
        permission.setPermissionName(dto.getPermissionName());
        return permission;
    }

    /**
     * Actualiza una entidad Permission existente con los datos del DTO.
     * Este método se utiliza en:
     * - actualizarPermiso(): para actualizar los campos del permiso existente
     * 
     * Separar esta lógica permite:
     * - Mantener la lógica de actualización en un solo lugar
     * - Evitar duplicación de código
     * - Facilitar cambios en la lógica de actualización
     */
    private void actualizarPermissionDesdeDto(Permission permission, PermissionRequesDto dto) {
        permission.setPermissionName(dto.getPermissionName());
    }
}
