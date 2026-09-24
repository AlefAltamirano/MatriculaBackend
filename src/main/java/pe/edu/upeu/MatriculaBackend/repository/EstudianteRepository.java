package pe.edu.upeu.MatriculaBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.MatriculaBackend.entity.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    boolean existsByCodigo(String codigo);
    boolean existsByDni(String dni);
}