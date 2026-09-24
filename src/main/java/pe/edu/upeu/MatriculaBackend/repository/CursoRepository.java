package pe.edu.upeu.MatriculaBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.MatriculaBackend.entity.Curso;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    boolean existsByCodigo(String codigo);
    List<Curso> findByCarreraId(Long carreraId);

    @Query("SELECT c FROM Curso c JOIN FETCH c.carrera " +
            "WHERE (:carreraId IS NULL OR c.carrera.id = :carreraId) " +
            "AND (:ciclo IS NULL OR c.ciclo = :ciclo) " +
            "AND (:conVacantes IS NULL OR (:conVacantes = true AND c.vacantes > 0) OR (:conVacantes = false AND c.vacantes = 0)) " +
            "AND (:nombre IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    List<Curso> buscarCursosConFiltros(@Param("carreraId") Long carreraId,
                                       @Param("ciclo") Integer ciclo,
                                       @Param("conVacantes") Boolean conVacantes,
                                       @Param("nombre") String nombre);
}