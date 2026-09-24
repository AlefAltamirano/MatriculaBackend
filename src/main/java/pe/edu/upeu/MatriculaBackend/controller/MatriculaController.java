package pe.edu.upeu.MatriculaBackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.MatriculaBackend.dto.request.MatriculaRequestDTO;
import pe.edu.upeu.MatriculaBackend.dto.response.MatriculaResponseDTO;
import pe.edu.upeu.MatriculaBackend.service.service.MatriculaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService matriculaService;

    @PostMapping
    public ResponseEntity<MatriculaResponseDTO> registrarMatricula(@Valid @RequestBody MatriculaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(matriculaService.registrarMatricula(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(matriculaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<MatriculaResponseDTO>> findAll() {
        return ResponseEntity.ok(matriculaService.findAll());
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<MatriculaResponseDTO> anularMatricula(@PathVariable Long id) {
        return ResponseEntity.ok(matriculaService.anularMatricula(id));
    }
}