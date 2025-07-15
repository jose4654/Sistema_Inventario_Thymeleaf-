package com.JOSE.Inventario.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JOSE.Inventario.DTO.CategoriaRequesDto;
import com.JOSE.Inventario.DTO.CategoriaResponseDto;
import com.JOSE.Inventario.Model.Categoria;
import com.JOSE.Inventario.Repository.ICategoriaRepository;

@Service
public class CategoriaService implements ICategoriaService {

	@Autowired
	private ICategoriaRepository categoriaRepository;

	@Override
	public List<CategoriaResponseDto> listaCategoria() {
		return categoriaRepository.findAll().stream()
			.map(this::convertirACategoriaResponseDto)
			.collect(Collectors.toList());
	}

	@Override
	public CategoriaResponseDto buscarCategoriaId(Long id) {
		Categoria categoria = categoriaRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
		return convertirACategoriaResponseDto(categoria);
	}

	@Override
	public void guardarCategoria(CategoriaRequesDto categoriaDto) {
		Categoria categoria = convertirACategoria(categoriaDto);
		categoriaRepository.save(categoria);
	}

	@Override
	public void eliminarCategoria(Long id) {
		categoriaRepository.deleteById(id);
	}
 //editar la categorioa
	@Override
	public CategoriaResponseDto actualizarCategoria(Long id, CategoriaRequesDto categoriaDto) {
		Categoria categoriaExistente = categoriaRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
		
		actualizarCategoriaDesdeDto(categoriaExistente, categoriaDto);
		//Aqui ya guarda la categoria actualizada en la base de datos
		Categoria categoriaActualizada = categoriaRepository.save(categoriaExistente);
		//llama al metodo para converti esa categoria en dto para devolverselo al controller y sepa que datos se ingresaron
		return convertirACategoriaResponseDto(categoriaActualizada);
	}
	//este es un metodo lo cual su funcion es recibir la categoria dto del controoles y setarla en la que se va enviar ala base de datos
	private void actualizarCategoriaDesdeDto(Categoria categoria, CategoriaRequesDto dto) {
		categoria.setNombre(dto.getNombre());
		categoria.setDescripcion(dto.getDescripcion());
		categoria.setEstado(dto.isEstado());
	}

	
	private CategoriaResponseDto convertirACategoriaResponseDto(Categoria categoria) {
		CategoriaResponseDto dto = new CategoriaResponseDto();
		dto.setId(categoria.getId());
		dto.setNombre(categoria.getNombre());
		dto.setDescripcion(categoria.getDescripcion());
		dto.setEstado(categoria.isEstado());
		// Aquí se debería convertir también los productos si es necesario
		return dto;
	}

	private Categoria convertirACategoria(CategoriaRequesDto dto) {
		Categoria categoria = new Categoria();
		categoria.setNombre(dto.getNombre());
		categoria.setDescripcion(dto.getDescripcion());
		categoria.setEstado(dto.isEstado());
		return categoria;
	}

	
}





