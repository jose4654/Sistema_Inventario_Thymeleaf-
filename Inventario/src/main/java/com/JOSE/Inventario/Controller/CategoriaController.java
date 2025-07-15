package com.JOSE.Inventario.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JOSE.Inventario.DTO.CategoriaRequesDto;
import com.JOSE.Inventario.DTO.CategoriaResponseDto;
import com.JOSE.Inventario.Service.CategoriaService;

@RequestMapping("categoria-app")//Esta anotación se utiliza para definir la URL o la ruta a la que debe responder un método dentro de un controlador. Es una parte fundamental de la creación de servicios web y aplicaciones web en Spring Boot.
//@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@PreAuthorize("denyAll()")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // Obtener todas las categorías
    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaResponseDto>> obtenerTodasLasCategorias() {
        return ResponseEntity.ok(categoriaService.listaCategoria());
    }

    // Buscar una categoría por ID
    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCategoriaPorId(@PathVariable Long id) {
        try {
            CategoriaResponseDto categoria = categoriaService.buscarCategoriaId(id);
            return ResponseEntity.ok(categoria);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada.");
        }
    }

    // Crear una nueva categoría
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public ResponseEntity<CategoriaResponseDto> crearCategoria(@RequestBody CategoriaRequesDto categoriaDto) {
        categoriaService.guardarCategoria(categoriaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.buscarCategoriaId(
            categoriaService.listaCategoria().stream()
                .filter(c -> c.getNombre().equals(categoriaDto.getNombre()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error al crear la categoría"))
                .getId()
        ));
    }

    // Actualizar una categoría existente
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaRequesDto categoriaDto) {
        try {
            CategoriaResponseDto categoriaActualizada = categoriaService.actualizarCategoria(id, categoriaDto);
            return ResponseEntity.ok(categoriaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada.");
        }
    }

    // Eliminar una categoría por ID
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        try {
            categoriaService.buscarCategoriaId(id); // Verificar si existe
            categoriaService.eliminarCategoria(id);
            return ResponseEntity.ok("Categoría eliminada correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada.");
        }
    }
}
