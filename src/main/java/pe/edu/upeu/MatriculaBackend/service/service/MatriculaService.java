package pe.edu.upeu.MatriculaBackend.service.service;

import pe.edu.upeu.MatriculaBackend.dto.request.MatriculaRequestDTO;
import pe.edu.upeu.MatriculaBackend.dto.response.MatriculaResponseDTO;

import java.util.List;

public interface MatriculaService {
    MatriculaResponseDTO registrarMatricula(MatriculaRequestDTO request);
    MatriculaResponseDTO findById(Long id);
    List<MatriculaResponseDTO> findAll();
    MatriculaResponseDTO anularMatricula(Long id);
}