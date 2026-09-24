package pe.edu.upeu.MatriculaBackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.MatriculaBackend.dto.request.CarreraRequestDTO;
import pe.edu.upeu.MatriculaBackend.dto.response.CarreraResponseDTO;
import pe.edu.upeu.MatriculaBackend.entity.Carrera;
import pe.edu.upeu.MatriculaBackend.exception.RecursoNoEncontradoException;
import pe.edu.upeu.MatriculaBackend.exception.ReglaNegocioException;
import pe.edu.upeu.MatriculaBackend.repository.CarreraRepository;
import pe.edu.upeu.MatriculaBackend.service.service.CarreraService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarreraServiceImpl implements CarreraService {

    private final CarreraRepository carreraRepository;

    @Override
    @Transactional
    public CarreraResponseDTO create(CarreraRequestDTO request) {
        log.info("Creando carrera: {}", request.getNombre());
        String nombreLimpio = request.getNombre().trim();

        if (carreraRepository.existsByNombreIgnoreCase(nombreLimpio)) {
            throw new ReglaNegocioException("Ya existe una carrera con el nombre: " + nombreLimpio);
        }

        Carrera carrera = Carrera.builder()
                .nombre(nombreLimpio)
                .descripcion(request.getDescripcion())
                .estado(request.getEstado())
                .build();

        return mapToResponse(carreraRepository.save(carrera));
    }

    @Override
    @Transactional
    public CarreraResponseDTO update(Long id, CarreraRequestDTO request) {
        log.info("Actualizando carrera con ID: {}", id);
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con ID: " + id));

        String nombreLimpio = request.getNombre().trim();
        if (!carrera.getNombre().equalsIgnoreCase(nombreLimpio) && carreraRepository.existsByNombreIgnoreCase(nombreLimpio)) {
            throw new ReglaNegocioException("Ya existe una carrera con el nombre: " + nombreLimpio);
        }

        carrera.setNombre(nombreLimpio);
        carrera.setDescripcion(request.getDescripcion());
        carrera.setEstado(request.getEstado());

        return mapToResponse(carreraRepository.save(carrera));
    }

    @Override
    @Transactional(readOnly = true)
    public CarreraResponseDTO findById(Long id) {
        return carreraRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarreraResponseDTO> findAll() {
        return carreraRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando carrera con ID: {}", id);
        if (!carreraRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Carrera no encontrada con ID: " + id);
        }
        carreraRepository.deleteById(id);
    }

    private CarreraResponseDTO mapToResponse(Carrera carrera) {
        return CarreraResponseDTO.builder()
                .id(carrera.getId())
                .nombre(carrera.getNombre())
                .descripcion(carrera.getDescripcion())
                .estado(carrera.getEstado())
                .fechaCreacion(carrera.getFechaCreacion())
                .fechaModificacion(carrera.getFechaModificacion())
                .build();
    }
}