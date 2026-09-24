package pe.edu.upeu.MatriculaBackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.MatriculaBackend.dto.request.CarreraRequestDTO;
import pe.edu.upeu.MatriculaBackend.dto.response.CarreraResponseDTO;
import pe.edu.upeu.MatriculaBackend.service.service.CarreraService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carreras")
@RequiredArgsConstructor
public class CarreraController {

    private final CarreraService carreraService;

    @PostMapping
    public ResponseEntity<CarreraResponseDTO> create(@Valid @RequestBody CarreraRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carreraService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarreraResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CarreraRequestDTO request) {
        return ResponseEntity.ok(carreraService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarreraResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(carreraService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<CarreraResponseDTO>> findAll() {
        return ResponseEntity.ok(carreraService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carreraService.delete(id);
        return ResponseEntity.noContent().build();
    }
}