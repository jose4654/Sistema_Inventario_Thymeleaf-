package com.JOSE.Inventario.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JOSE.Inventario.DTO.PermissionRequesDto;
import com.JOSE.Inventario.DTO.PermissionResponseDto;
import com.JOSE.Inventario.Service.IPermissionService;

@RestController
@RequestMapping("/api/permissions")
@PreAuthorize("denyAll()")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping
    public ResponseEntity<List<PermissionResponseDto>> getAllPermissions() {
        return ResponseEntity.ok(permissionService.listaPermisos());
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getPermissionById(@PathVariable Long id) {
        try {
            PermissionResponseDto permission = permissionService.buscarPermisoId(id);
            return ResponseEntity.ok(permission);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Permiso no encontrado");
        }
    }
    

    //@PreAuthorize("hasAuthority('CREATE')")
    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<?> createPermission(@RequestBody PermissionRequesDto permissionDto) {
        try {
           

            PermissionResponseDto newPermission = permissionService.guardarPermiso(permissionDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPermission);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al crear el permiso: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePermission(@PathVariable Long id, @RequestBody PermissionRequesDto permissionDto) {
        try {
        

            PermissionResponseDto updatedPermission = permissionService.actualizarPermiso(id, permissionDto);
            return ResponseEntity.ok(updatedPermission);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Permiso no encontrado");
        }
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable Long id) {
        try {
            permissionService.buscarPermisoId(id); // Verificar si existe
            permissionService.eliminarPermiso(id);
            return ResponseEntity.ok("Permiso eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Permiso no encontrado");
        }
    }
}
