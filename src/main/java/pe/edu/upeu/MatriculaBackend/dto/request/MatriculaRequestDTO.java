package pe.edu.upeu.MatriculaBackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class MatriculaRequestDTO {

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long estudianteId;

    @NotBlank(message = "El período es obligatorio")
    @Pattern(regexp = "^\\d{4}-[12]$", message = "El período debe tener el formato YYYY-1 o YYYY-2 (Ej. 2026-2)")
    private String periodo;

    @NotEmpty(message = "La matrícula debe contener al menos un curso")
    private List<DetalleMatriculaRequestDTO> detalles;
}