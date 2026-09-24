package pe.edu.upeu.MatriculaBackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.MatriculaBackend.dto.request.DetalleMatriculaRequestDTO;
import pe.edu.upeu.MatriculaBackend.dto.request.MatriculaRequestDTO;
import pe.edu.upeu.MatriculaBackend.dto.response.DetalleMatriculaResponseDTO;
import pe.edu.upeu.MatriculaBackend.dto.response.MatriculaResponseDTO;
import pe.edu.upeu.MatriculaBackend.entity.Curso;
import pe.edu.upeu.MatriculaBackend.entity.DetalleMatricula;
import pe.edu.upeu.MatriculaBackend.entity.Estudiante;
import pe.edu.upeu.MatriculaBackend.entity.Matricula;
import pe.edu.upeu.MatriculaBackend.enums.EstadoMatricula;
import pe.edu.upeu.MatriculaBackend.exception.RecursoNoEncontradoException;
import pe.edu.upeu.MatriculaBackend.exception.ReglaNegocioException;
import pe.edu.upeu.MatriculaBackend.repository.CursoRepository;
import pe.edu.upeu.MatriculaBackend.repository.EstudianteRepository;
import pe.edu.upeu.MatriculaBackend.repository.MatriculaRepository;
import pe.edu.upeu.MatriculaBackend.service.service.MatriculaService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    @Value("${matricula.costo-credito:120.00}")
    private BigDecimal costoCredito;

    @Override
    @Transactional
    public MatriculaResponseDTO registrarMatricula(MatriculaRequestDTO request) {
        log.info("Iniciando registro de matrícula para el estudiante ID: {} en el período: {}", request.getEstudianteId(), request.getPeriodo());

        // 1. Validar Estudiante
        Estudiante estudiante = estudianteRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + request.getEstudianteId()));

        // RN-01: Estudiante Activo
        if (!estudiante.getEstado()) {
            throw new ReglaNegocioException("RN-01: El estudiante se encuentra inactivo.");
        }

        // RN-03: No tener más de una matrícula REGISTRADA en el mismo periodo
        if (matriculaRepository.existsByEstudianteIdAndPeriodoAndEstado(estudiante.getId(), request.getPeriodo(), EstadoMatricula.REGISTRADA)) {
            throw new ReglaNegocioException("RN-03: El estudiante ya cuenta con una matrícula activa en el período " + request.getPeriodo());
        }

        Matricula matricula = Matricula.builder()
                .fecha(LocalDateTime.now())
                .periodo(request.getPeriodo().trim())
                .estudiante(estudiante)
                .estado(EstadoMatricula.REGISTRADA)
                .detalles(new ArrayList<>())
                .build();

        int sumaCreditos = 0;
        BigDecimal montoAcumulado = BigDecimal.ZERO;

        // Processar detalles
        for (DetalleMatriculaRequestDTO detDto : request.getDetalles()) {
            Curso curso = cursoRepository.findById(detDto.getCursoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con ID: " + detDto.getCursoId()));

            // RN-01: Curso Activo y de la misma Carrera
            if (!curso.getEstado()) {
                throw new ReglaNegocioException("RN-01: El curso '" + curso.getNombre() + "' está inactivo.");
            }
            if (!curso.getCarrera().getId().equals(estudiante.getCarrera().getId())) {
                throw new ReglaNegocioException("RN-01: El curso '" + curso.getNombre() + "' no pertenece a la carrera del estudiante.");
            }

            // RN-02: Verificar Vacantes
            if (curso.getVacantes() <= 0) {
                throw new ReglaNegocioException("RN-02: El curso '" + curso.getNombre() + "' no cuenta con vacantes disponibles.");
            }

            // Descontar vacante
            curso.setVacantes(curso.getVacantes() - 1);
            cursoRepository.save(curso);

            // RN-04: Calcular costo por curso
            BigDecimal costoCurso = BigDecimal.valueOf(curso.getCreditos())
                    .multiply(costoCredito)
                    .setScale(2, RoundingMode.HALF_UP);

            sumaCreditos += curso.getCreditos();
            montoAcumulado = montoAcumulado.add(costoCurso);

            DetalleMatricula detalle = DetalleMatricula.builder()
                    .curso(curso)
                    .creditos(curso.getCreditos())
                    .costo(costoCurso)
                    .build();

            matricula.agregarDetalle(detalle);
        }

        // RN-04: Máximo 20 créditos
        if (sumaCreditos > 20) {
            throw new ReglaNegocioException("RN-04: La matrícula no puede superar los 20 créditos. Total solicitado: " + sumaCreditos);
        }

        matricula.setTotalCreditos(sumaCreditos);
        matricula.setMontoTotal(montoAcumulado.setScale(2, RoundingMode.HALF_UP));

        Matricula guardada = matriculaRepository.save(matricula);
        log.info("Matrícula registrada exitosamente con ID: {}", guardada.getId());

        return mapToResponse(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public MatriculaResponseDTO findById(Long id) {
        return matriculaRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RecursoNoEncontradoException("Matrícula no encontrada con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatriculaResponseDTO> findAll() {
        return matriculaRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public MatriculaResponseDTO anularMatricula(Long id) {
        log.info("Anulando matrícula con ID: {}", id);
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Matrícula no encontrada con ID: " + id));

        if (matricula.getEstado() == EstadoMatricula.ANULADA) {
            throw new ReglaNegocioException("La matrícula ya se encuentra anulada.");
        }

        matricula.setEstado(EstadoMatricula.ANULADA);

        // RN-02: Devolver vacantes a cada curso
        for (DetalleMatricula detalle : matricula.getDetalles()) {
            Curso curso = detalle.getCurso();
            curso.setVacantes(curso.getVacantes() + 1);
            cursoRepository.save(curso);
        }

        return mapToResponse(matriculaRepository.save(matricula));
    }

    private MatriculaResponseDTO mapToResponse(Matricula matricula) {
        List<DetalleMatriculaResponseDTO> detallesDto = matricula.getDetalles().stream()
                .map(d -> DetalleMatriculaResponseDTO.builder()
                        .id(d.getId())
                        .cursoId(d.getCurso().getId())
                        .cursoCodigo(d.getCurso().getCodigo())
                        .cursoNombre(d.getCurso().getNombre())
                        .creditos(d.getCreditos())
                        .costo(d.getCosto())
                        .build())
                .toList();

        return MatriculaResponseDTO.builder()
                .id(matricula.getId())
                .fecha(matricula.getFecha())
                .periodo(matricula.getPeriodo())
                .estudianteId(matricula.getEstudiante().getId())
                .estudianteCodigo(matricula.getEstudiante().getCodigo())
                .estudianteNombreCompleto(matricula.getEstudiante().getNombres() + " " + matricula.getEstudiante().getApellidos())
                .carreraNombre(matricula.getEstudiante().getCarrera().getNombre())
                .totalCreditos(matricula.getTotalCreditos())
                .montoTotal(matricula.getMontoTotal())
                .estado(matricula.getEstado())
                .detalles(detallesDto)
                .build();
    }
}