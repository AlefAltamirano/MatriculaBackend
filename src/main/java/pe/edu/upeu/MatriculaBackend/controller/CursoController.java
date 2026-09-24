package pe.edu.upeu.MatriculaBackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.MatriculaBackend.dto.request.CursoRequestDTO;
import pe.edu.upeu.MatriculaBackend.dto.response.CursoResponseDTO;
import pe.edu.upeu.MatriculaBackend.service.service.CursoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @PostMapping("/cursos")
    public ResponseEntity<CursoResponseDTO> create(@Valid @RequestBody CursoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.create(request));
    }

    @PutMapping("/cursos/{id}")
    public ResponseEntity<CursoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CursoRequestDTO request) {
        return ResponseEntity.ok(cursoService.update(id, request));
    }

    @GetMapping("/cursos/{id}")
    public ResponseEntity<CursoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.findById(id));
    }

    @GetMapping("/cursos")
    public ResponseEntity<List<CursoResponseDTO>> findAll() {
        return ResponseEntity.ok(cursoService.findAll());
    }

    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/carreras/{carreraId}/cursos")
    public ResponseEntity<List<CursoResponseDTO>> findByCarrera(@PathVariable Long carreraId) {
        return ResponseEntity.ok(cursoService.findByCarreraId(carreraId));
    }

    @GetMapping("/cursos/buscar")
    public ResponseEntity<List<CursoResponseDTO>> buscarCursos(
            @RequestParam(required = false) Long carreraId,
            @RequestParam(required = false) Integer ciclo,
            @RequestParam(required = false) Boolean conVacantes,
            @RequestParam(required = false) String nombre,
            @RequestParam(defaultValue = "nombre") String orden,
            @RequestParam(defaultValue = "asc") String dir) {
        return ResponseEntity.ok(cursoService.buscarCursos(carreraId, ciclo, conVacantes, nombre, orden, dir));
    }
}