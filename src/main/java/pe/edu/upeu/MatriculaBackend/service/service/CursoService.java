package pe.edu.upeu.MatriculaBackend.service.service;

import pe.edu.upeu.MatriculaBackend.dto.request.CursoRequestDTO;
import pe.edu.upeu.MatriculaBackend.dto.response.CursoResponseDTO;
import pe.edu.upeu.MatriculaBackend.service.generic.CrudService;

import java.util.List;

public interface CursoService extends CrudService<CursoRequestDTO, CursoResponseDTO, Long> {
    List<CursoResponseDTO> findByCarreraId(Long carreraId);
    List<CursoResponseDTO> buscarCursos(Long carreraId, Integer ciclo, Boolean conVacantes, String nombre, String ordenarPor, String direccion);
}