package com.JOSE.Inventario.Service;

import java.util.List;
import com.JOSE.Inventario.DTO.UserSecRequesDto;
import com.JOSE.Inventario.DTO.UserSecResponseDto;

public interface IUserService {
    public List<UserSecResponseDto> listaUsuarios();
    public UserSecResponseDto buscarUsuarioId(Long id);
    public UserSecResponseDto buscarUsuarioPorUsername(String username);
    public UserSecResponseDto guardarUsuario(UserSecRequesDto usuarioDto);
    public UserSecResponseDto guardarUsuariocliente(UserSecRequesDto usuarioDto);
    public void eliminarUsuario(Long id);
    public UserSecResponseDto actualizarUsuario(Long id, UserSecRequesDto usuarioDto);
    public UserSecResponseDto cambiarEstadoUsuario(Long id, boolean estado);
}

