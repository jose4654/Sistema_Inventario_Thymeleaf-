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

import com.JOSE.Inventario.DTO.RoleRequesDto;
import com.JOSE.Inventario.DTO.RoleResponseDto;
import com.JOSE.Inventario.Service.IRoleService;

@RestController
@RequestMapping("/api/roles")
@PreAuthorize("denyAll()")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.listaRoles());
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        try {
            RoleResponseDto role = roleService.buscarRoleId(id);
            return ResponseEntity.ok(role);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }
    }
     @PreAuthorize("permitAll()")
   // @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleRequesDto roleDto) {
        try {
           

            RoleResponseDto newRole = roleService.guardarRole(roleDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newRole);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al crear el rol: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody RoleRequesDto roleDto) {
        try {
        

            RoleResponseDto updatedRole = roleService.actualizarRole(id, roleDto);
            return ResponseEntity.ok(updatedRole);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        try {
            roleService.buscarRoleId(id); // Verificar si existe
            roleService.eliminarRole(id);
            return ResponseEntity.ok("Rol eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }
    }
}
