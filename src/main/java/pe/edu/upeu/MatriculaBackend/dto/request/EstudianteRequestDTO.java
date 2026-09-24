package pe.edu.upeu.MatriculaBackend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EstudianteRequestDTO {

    @NotBlank(message = "El código es obligatorio")
    @Pattern(regexp = "^\\d{9}$", message = "El código debe tener exactamente 9 dígitos")
    private String codigo;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^\\d{8}$", message = "El DNI debe tener exactamente 8 dígitos")
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "Máximo 100 caracteres")
    private String nombres;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "Máximo 100 caracteres")
    private String apellidos;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

    @NotNull(message = "La carrera es obligatoria")
    private Long carreraId;
}