package pe.edu.upeu.MatriculaBackend.dto.response;

import lombok.Builder;
import lombok.Data;
import pe.edu.upeu.MatriculaBackend.enums.EstadoMatricula;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MatriculaResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private String periodo;
    private Long estudianteId;
    private String estudianteCodigo;
    private String estudianteNombreCompleto;
    private String carreraNombre;
    private Integer totalCreditos;
    private BigDecimal montoTotal;
    private EstadoMatricula estado;
    private List<DetalleMatriculaResponseDTO> detalles;
}