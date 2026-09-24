package pe.edu.upeu.MatriculaBackend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CursoRequestDTO {

    @NotBlank(message = "El código es obligatorio")
    @Pattern(regexp = "^[A-Z]{2}\\d{3}$", message = "El código debe tener el formato de 2 letras mayúsculas y 3 números (Ej: IS401)")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    private String nombre;

    @NotNull(message = "Los créditos son obligatorios")
    @Min(value = 1, message = "Mínimo 1 crédito")
    @Max(value = 6, message = "Máximo 6 créditos")
    private Integer creditos;

    @NotNull(message = "El ciclo es obligatorio")
    @Min(value = 1, message = "Mínimo ciclo 1")
    @Max(value = 10, message = "Máximo ciclo 10")
    private Integer ciclo;

    @NotNull(message = "Las vacantes son obligatorias")
    @Min(value = 0, message = "Las vacantes no pueden ser negativas")
    private Integer vacantes;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

    @NotNull(message = "La carrera es obligatoria")
    private Long carreraId;
}