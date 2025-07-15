package com.JOSE.Inventario.Service;

import java.util.List;
import com.JOSE.Inventario.DTO.CategoriaRequesDto;
import com.JOSE.Inventario.DTO.CategoriaResponseDto;

public interface ICategoriaService {
	public List<CategoriaResponseDto> listaCategoria();
	public CategoriaResponseDto buscarCategoriaId(Long id);
	public void guardarCategoria(CategoriaRequesDto categoriaDto);
	public void eliminarCategoria(Long id);
	public CategoriaResponseDto actualizarCategoria(Long id, CategoriaRequesDto categoriaDto);
}
