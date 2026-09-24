package pe.edu.upeu.MatriculaBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.MatriculaBackend.entity.Carrera;

import java.util.Optional;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
    Optional<Carrera> findByNombreIgnoreCase(String nombre);
}