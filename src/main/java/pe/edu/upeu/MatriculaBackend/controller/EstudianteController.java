package pe.edu.upeu.MatriculaBackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.MatriculaBackend.dto.request.EstudianteRequestDTO;
import pe.edu.upeu.MatriculaBackend.dto.response.EstudianteResponseDTO;
import pe.edu.upeu.MatriculaBackend.dto.response.MatriculaResponseDTO;
import pe.edu.upeu.MatriculaBackend.service.service.EstudianteService;
import pe.edu.upeu.MatriculaBackend.service.service.MatriculaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {
    private final MatriculaService matriculaService;
    private final EstudianteService estudianteService;

    @PostMapping
    public ResponseEntity<EstudianteResponseDTO> create(@Valid @RequestBody EstudianteRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estudianteService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> update(@PathVariable Long id, @Valid @RequestBody EstudianteRequestDTO request) {
        return ResponseEntity.ok(estudianteService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(estudianteService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<EstudianteResponseDTO>> findAll() {
        return ResponseEntity.ok(estudianteService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        estudianteService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/matriculas")
    public ResponseEntity<List<MatriculaResponseDTO>> getHistorialMatriculas(
            @PathVariable Long id,
            @RequestParam(required = false) String periodo) {
        return ResponseEntity.ok(matriculaService.getHistorialEstudiante(id, periodo));
    }
}