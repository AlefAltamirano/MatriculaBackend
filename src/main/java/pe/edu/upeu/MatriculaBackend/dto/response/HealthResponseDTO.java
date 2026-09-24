package pe.edu.upeu.MatriculaBackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthResponseDTO {
    private String status;
    private String database;
    private LocalDateTime timestamp;
}