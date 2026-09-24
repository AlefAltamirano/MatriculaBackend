package pe.edu.upeu.MatriculaBackend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DetalleMatriculaResponseDTO {
    private Long id;
    private Long cursoId;
    private String cursoCodigo;
    private String cursoNombre;
    private Integer creditos;
    private BigDecimal costo;
}