package pe.edu.upeu.MatriculaBackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.MatriculaBackend.dto.request.EstudianteRequestDTO;
import pe.edu.upeu.MatriculaBackend.dto.response.EstudianteResponseDTO;
import pe.edu.upeu.MatriculaBackend.entity.Carrera;
import pe.edu.upeu.MatriculaBackend.entity.Estudiante;
import pe.edu.upeu.MatriculaBackend.exception.RecursoNoEncontradoException;
import pe.edu.upeu.MatriculaBackend.exception.ReglaNegocioException;
import pe.edu.upeu.MatriculaBackend.repository.CarreraRepository;
import pe.edu.upeu.MatriculaBackend.repository.EstudianteRepository;
import pe.edu.upeu.MatriculaBackend.service.service.EstudianteService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final CarreraRepository carreraRepository;

    @Override
    @Transactional
    public EstudianteResponseDTO create(EstudianteRequestDTO request) {
        log.info("Registrando estudiante: {}", request.getNombres());
        if (estudianteRepository.existsByCodigo(request.getCodigo())) {
            throw new ReglaNegocioException("Ya existe un estudiante con el código: " + request.getCodigo());
        }
        if (estudianteRepository.existsByDni(request.getDni())) {
            throw new ReglaNegocioException("Ya existe un estudiante con el DNI: " + request.getDni());
        }

        Carrera carrera = carreraRepository.findById(request.getCarreraId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con ID: " + request.getCarreraId()));

        Estudiante estudiante = Estudiante.builder()
                .codigo(request.getCodigo().trim())
                .dni(request.getDni().trim())
                .nombres(request.getNombres().trim())
                .apellidos(request.getApellidos().trim())
                .email(request.getEmail().trim().toLowerCase())
                .estado(request.getEstado())
                .carrera(carrera)
                .build();

        return mapToResponse(estudianteRepository.save(estudiante));
    }

    @Override
    @Transactional
    public EstudianteResponseDTO update(Long id, EstudianteRequestDTO request) {
        log.info("Actualizando estudiante con ID: {}", id);
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + id));

        if (!estudiante.getCodigo().equalsIgnoreCase(request.getCodigo()) && estudianteRepository.existsByCodigo(request.getCodigo())) {
            throw new ReglaNegocioException("Ya existe un estudiante con el código: " + request.getCodigo());
        }
        if (!estudiante.getDni().equalsIgnoreCase(request.getDni()) && estudianteRepository.existsByDni(request.getDni())) {
            throw new ReglaNegocioException("Ya existe un estudiante con el DNI: " + request.getDni());
        }

        Carrera carrera = carreraRepository.findById(request.getCarreraId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con ID: " + request.getCarreraId()));

        estudiante.setCodigo(request.getCodigo().trim());
        estudiante.setDni(request.getDni().trim());
        estudiante.setNombres(request.getNombres().trim());
        estudiante.setApellidos(request.getApellidos().trim());
        estudiante.setEmail(request.getEmail().trim().toLowerCase());
        estudiante.setEstado(request.getEstado());
        estudiante.setCarrera(carrera);

        return mapToResponse(estudianteRepository.save(estudiante));
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteResponseDTO findById(Long id) {
        return estudianteRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> findAll() {
        return estudianteRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando estudiante con ID: {}", id);
        if (!estudianteRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + id);
        }
        estudianteRepository.deleteById(id);
    }

    private EstudianteResponseDTO mapToResponse(Estudiante estudiante) {
        return EstudianteResponseDTO.builder()
                .id(estudiante.getId())
                .codigo(estudiante.getCodigo())
                .dni(estudiante.getDni())
                .nombres(estudiante.getNombres())
                .apellidos(estudiante.getApellidos())
                .email(estudiante.getEmail())
                .estado(estudiante.getEstado())
                .carreraId(estudiante.getCarrera().getId())
                .carreraNombre(estudiante.getCarrera().getNombre())
                .fechaCreacion(estudiante.getFechaCreacion())
                .fechaModificacion(estudiante.getFechaModificacion())
                .build();
    }
}