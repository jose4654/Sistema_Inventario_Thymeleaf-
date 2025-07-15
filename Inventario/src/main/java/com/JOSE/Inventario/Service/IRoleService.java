package com.JOSE.Inventario.Service;

import java.util.List;
import com.JOSE.Inventario.DTO.RoleRequesDto;
import com.JOSE.Inventario.DTO.RoleResponseDto;

public interface IRoleService {
    public List<RoleResponseDto> listaRoles();
    public RoleResponseDto buscarRoleId(Long id);
    public RoleResponseDto guardarRole(RoleRequesDto roleDto);
    public void eliminarRole(Long id);
    public RoleResponseDto actualizarRole(Long id, RoleRequesDto roleDto);
}
