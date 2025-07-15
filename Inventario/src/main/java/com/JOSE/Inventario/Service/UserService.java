package com.JOSE.Inventario.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.JOSE.Inventario.DTO.RoleResponseDto;
import com.JOSE.Inventario.DTO.UserSecRequesDto;
import com.JOSE.Inventario.DTO.UserSecResponseDto;
import com.JOSE.Inventario.DTO.PermissionResponseDto;
import com.JOSE.Inventario.Model.Role;
import com.JOSE.Inventario.Model.UserSec;
import com.JOSE.Inventario.Repository.IRoleRepository;
import com.JOSE.Inventario.Repository.IUserRepository;

/**
 * Servicio que implementa la lógica de negocio para la gestión de usuarios.
 * Utiliza métodos de conversión separados para mantener el código organizado y reutilizable.
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserSecResponseDto> listaUsuarios() {
        return userRepository.findAll().stream()
            .map(this::convertirAUserResponseDto)
            .collect(Collectors.toList());
    }

    @Override
    public UserSecResponseDto buscarUsuarioId(Long id) {
        UserSec usuario = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirAUserResponseDto(usuario);
    }

    @Override
    public UserSecResponseDto buscarUsuarioPorUsername(String username) {
        UserSec usuario = userRepository.findUserEntityByUserName(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirAUserResponseDto(usuario);
    }

    @Override
    public UserSecResponseDto guardarUsuario(UserSecRequesDto usuarioDto) {
        UserSec usuario = convertirAUser(usuarioDto);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setAccountNotExpired(true);
        usuario.setAccountNotLocked(true);
        usuario.setCredentialNotExpired(true);
        
        // Establecer roles si se proporcionaron IDs
        if (usuarioDto.getRolIds() != null && !usuarioDto.getRolIds().isEmpty()) {
            Set<Role> roles = usuarioDto.getRolIds().stream()
                .map(roleId -> roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleId)))
                .collect(Collectors.toSet());
            usuario.setRolesList(roles);
        }
        
        UserSec usuarioGuardado = userRepository.save(usuario);
        return convertirAUserResponseDto(usuarioGuardado);
    }
    
    
    @Override
	public UserSecResponseDto guardarUsuariocliente(UserSecRequesDto usuarioDto) {
        UserSec usuario = convertirAUser(usuarioDto);
        Set<Role> roles = new HashSet<>();

        // Buscar el rol 'USER' en la base de datos por su ID (asumiendo que el ID 2L es para el rol 'USER')
        Role userRole = roleRepository.findById(2L)
                                    .orElseThrow(() -> new RuntimeException("Rol 'USER' con ID 2 no encontrado en la base de datos. Asegúrate de que exista."));

        roles.add(userRole); // Añadir el rol obtenido de la base de datos
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setAccountNotExpired(true);
        usuario.setAccountNotLocked(true);
        usuario.setCredentialNotExpired(true);
        usuario.setRolesList(roles);

        UserSec usuarioGuardado = userRepository.save(usuario);

		return convertirAUserResponseDto(usuarioGuardado);
	}

    @Override
    public void eliminarUsuario(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserSecResponseDto actualizarUsuario(Long id, UserSecRequesDto usuarioDto) {
        UserSec usuarioExistente = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        actualizarUsuarioDesdeDto(usuarioExistente, usuarioDto);
        
        // Actualizar roles si se proporcionaron IDs
        if (usuarioDto.getRolIds() != null && !usuarioDto.getRolIds().isEmpty()) {
            Set<Role> roles = usuarioDto.getRolIds().stream()
                .map(roleId -> roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleId)))
                .collect(Collectors.toSet());
            usuarioExistente.setRolesList(roles);
        }
        
        UserSec usuarioActualizado = userRepository.save(usuarioExistente);
        return convertirAUserResponseDto(usuarioActualizado);
    }

    @Override
    public UserSecResponseDto cambiarEstadoUsuario(Long id, boolean estado) {
        UserSec usuario = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setEnable(estado);
        UserSec usuarioActualizado = userRepository.save(usuario);
        return convertirAUserResponseDto(usuarioActualizado);
    }

    /**
     * Convierte una entidad UserSec a un DTO de respuesta.
     * Este método se utiliza en:
     * - listaUsuarios(): para convertir cada usuario de la lista
     * - buscarUsuarioId(): para convertir el usuario encontrado
     * - buscarUsuarioPorUsername(): para convertir el usuario encontrado
     * - guardarUsuario(): para convertir el usuario guardado
     * - actualizarUsuario(): para convertir el usuario actualizado
     * - cambiarEstadoUsuario(): para convertir el usuario actualizado
     * 
     * Separar esta lógica permite:
     * - Reutilizar el código de conversión
     * - Mantener la consistencia en la conversión
     * - Facilitar cambios en la estructura del DTO
     * - Controlar qué información se expone al cliente
     */
    private UserSecResponseDto convertirAUserResponseDto(UserSec usuario) {
        UserSecResponseDto dto = new UserSecResponseDto();
        dto.setId(usuario.getId());
        dto.setUserName(usuario.getUserName());
        dto.setEnable(usuario.isEnable());
        dto.setAccountNotExpired(usuario.isAccountNotExpired());
        dto.setAccountNotLocked(usuario.isAccountNotLocked());
        dto.setCredentialNotExpired(usuario.isCredentialNotExpired());
        
        // Convertir los roles a DTOs
        List<RoleResponseDto> rolesDto = usuario.getRolesList().stream()
            .map(this::convertirRoleADto)
            .collect(Collectors.toList());
        dto.setRoles(rolesDto);
        
        return dto;
    }

    /**
     * Convierte un DTO de solicitud a una entidad UserSec.
     * Este método se utiliza en:
     * - guardarUsuario(): para convertir el DTO antes de guardar
     * 
     * Separar esta lógica permite:
     * - Validar y transformar los datos antes de crear la entidad
     * - Mantener la lógica de creación de entidades en un solo lugar
     * - Facilitar cambios en la estructura de la entidad
     * - Aplicar reglas de negocio durante la conversión
     */
    private UserSec convertirAUser(UserSecRequesDto dto) {
        UserSec usuario = new UserSec();
        usuario.setUserName(dto.getUserName());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setEnable(dto.isEnable());
        usuario.setAccountNotExpired(true);
        usuario.setAccountNotLocked(true);
        usuario.setCredentialNotExpired(true);
        
        // Establecer los roles si se proporcionaron IDs
        if (dto.getRolIds() != null && !dto.getRolIds().isEmpty()) {
            Set<Role> roles = dto.getRolIds().stream()
                .map(roleId -> roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleId)))
                .collect(Collectors.toSet());
            usuario.setRolesList(roles);
        } else {
            usuario.setRolesList(new HashSet<>());
        }
        
        return usuario;
    }

    /**
     * Actualiza una entidad UserSec existente con los datos del DTO.
     * Este método se utiliza en:
     * - actualizarUsuario(): para actualizar los campos del usuario existente
     * 
     * Separar esta lógica permite:
     * - Mantener la lógica de actualización en un solo lugar
     * - Evitar duplicación de código
     * - Facilitar cambios en la lógica de actualización
     * - Aplicar reglas de negocio durante la actualización
     */
    private void actualizarUsuarioDesdeDto(UserSec usuario, UserSecRequesDto dto) {
        usuario.setUserName(dto.getUserName());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        usuario.setEnable(dto.isEnable());
        
        // Actualizar los roles si se proporcionaron IDs
        if (dto.getRolIds() != null && !dto.getRolIds().isEmpty()) {
            Set<Role> roles = dto.getRolIds().stream()
                .map(roleId -> roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleId)))
                .collect(Collectors.toSet());
            usuario.setRolesList(roles);
        }
    }

    /**
     * Convierte una entidad Role a un DTO de respuesta.
     * Este método es utilizado internamente para convertir los roles de un usuario.
     */
    private RoleResponseDto convertirRoleADto(Role role) {
        RoleResponseDto dto = new RoleResponseDto();
        dto.setId(role.getId());
        dto.setRole(role.getRole());
        
        // Convertir los permisos a DTOs si existen
        if (role.getPermissionsList() != null && !role.getPermissionsList().isEmpty()) {
            List<PermissionResponseDto> permisosDto = role.getPermissionsList().stream()
                .map(permiso -> {
                    PermissionResponseDto permisoDto = new PermissionResponseDto();
                    permisoDto.setId(permiso.getId());
                    permisoDto.setPermissionName(permiso.getPermissionName());
                    return permisoDto;
                })
                .collect(Collectors.toList());
            dto.setPermissions(permisosDto);
        }
        
        return dto;
    }

	
}
