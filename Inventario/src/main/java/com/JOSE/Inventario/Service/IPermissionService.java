package com.JOSE.Inventario.Service;

import java.util.List;
import com.JOSE.Inventario.DTO.PermissionRequesDto;
import com.JOSE.Inventario.DTO.PermissionResponseDto;

public interface IPermissionService {
    public List<PermissionResponseDto> listaPermisos();
    public PermissionResponseDto buscarPermisoId(Long id);
    public PermissionResponseDto guardarPermiso(PermissionRequesDto permisoDto);
    public void eliminarPermiso(Long id);
    public PermissionResponseDto actualizarPermiso(Long id, PermissionRequesDto permisoDto);
}
