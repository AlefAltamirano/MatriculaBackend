package pe.edu.upeu.MatriculaBackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.MatriculaBackend.dto.request.CursoRequestDTO;
import pe.edu.upeu.MatriculaBackend.dto.response.CursoResponseDTO;
import pe.edu.upeu.MatriculaBackend.entity.Carrera;
import pe.edu.upeu.MatriculaBackend.entity.Curso;
import pe.edu.upeu.MatriculaBackend.exception.RecursoNoEncontradoException;
import pe.edu.upeu.MatriculaBackend.exception.ReglaNegocioException;
import pe.edu.upeu.MatriculaBackend.repository.CarreraRepository;
import pe.edu.upeu.MatriculaBackend.repository.CursoRepository;
import pe.edu.upeu.MatriculaBackend.service.service.CursoService;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final CarreraRepository carreraRepository;

    @Override
    @Transactional
    public CursoResponseDTO create(CursoRequestDTO request) {
        log.info("Creando curso: {}", request.getNombre());
        if (cursoRepository.existsByCodigo(request.getCodigo())) {
            throw new ReglaNegocioException("Ya existe un curso registrado con el código: " + request.getCodigo());
        }

        Carrera carrera = carreraRepository.findById(request.getCarreraId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con ID: " + request.getCarreraId()));

        Curso curso = Curso.builder()
                .codigo(request.getCodigo().trim())
                .nombre(request.getNombre().trim())
                .creditos(request.getCreditos())
                .ciclo(request.getCiclo())
                .vacantes(request.getVacantes())
                .estado(request.getEstado())
                .carrera(carrera)
                .build();

        return mapToResponse(cursoRepository.save(curso));
    }

    @Override
    @Transactional
    public CursoResponseDTO update(Long id, CursoRequestDTO request) {
        log.info("Actualizando curso con ID: {}", id);
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con ID: " + id));

        if (!curso.getCodigo().equalsIgnoreCase(request.getCodigo()) && cursoRepository.existsByCodigo(request.getCodigo())) {
            throw new ReglaNegocioException("Ya existe un curso registrado con el código: " + request.getCodigo());
        }

        Carrera carrera = carreraRepository.findById(request.getCarreraId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con ID: " + request.getCarreraId()));

        curso.setCodigo(request.getCodigo().trim());
        curso.setNombre(request.getNombre().trim());
        curso.setCreditos(request.getCreditos());
        curso.setCiclo(request.getCiclo());
        curso.setVacantes(request.getVacantes());
        curso.setEstado(request.getEstado());
        curso.setCarrera(carrera);

        return mapToResponse(cursoRepository.save(curso));
    }

    @Override
    @Transactional(readOnly = true)
    public CursoResponseDTO findById(Long id) {
        return cursoRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDTO> findAll() {
        return cursoRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando curso con ID: {}", id);
        if (!cursoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Curso no encontrado con ID: " + id);
        }
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDTO> findByCarreraId(Long carreraId) {
        if (!carreraRepository.existsById(carreraId)) {
            throw new RecursoNoEncontradoException("Carrera no encontrada con ID: " + carreraId);
        }
        return cursoRepository.findByCarreraId(carreraId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDTO> buscarCursos(Long carreraId, Integer ciclo, Boolean conVacantes, String nombre, String ordenarPor, String direccion) {
        log.info("Buscando cursos con filtros combinados");
        List<Curso> resultados = cursoRepository.buscarCursosConFiltros(carreraId, ciclo, conVacantes, nombre);

        // Ordenamiento dinámico
        Comparator<Curso> comparator = switch (ordenarPor != null ? ordenarPor.toLowerCase() : "nombre") {
            case "creditos" -> Comparator.comparing(Curso::getCreditos);
            case "vacantes" -> Comparator.comparing(Curso::getVacantes);
            default -> Comparator.comparing(Curso::getNombre);
        };

        if ("desc".equalsIgnoreCase(direccion)) {
            comparator = comparator.reversed();
        }

        return resultados.stream()
                .sorted(comparator)
                .map(this::mapToResponse)
                .toList();
    }

    private CursoResponseDTO mapToResponse(Curso curso) {
        return CursoResponseDTO.builder()
                .id(curso.getId())
                .codigo(curso.getCodigo())
                .nombre(curso.getNombre())
                .creditos(curso.getCreditos())
                .ciclo(curso.getCiclo())
                .vacantes(curso.getVacantes())
                .estado(curso.getEstado())
                .carreraId(curso.getCarrera().getId())
                .carreraNombre(curso.getCarrera().getNombre())
                .fechaCreacion(curso.getFechaCreacion())
                .fechaModificacion(curso.getFechaModificacion())
                .build();
    }
}