package pe.edu.upeu.MatriculaBackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upeu.MatriculaBackend.dto.response.HealthResponseDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/health")
@RequiredArgsConstructor
public class HealthController {

    private final DataSource dataSource;

    @GetMapping
    public ResponseEntity<HealthResponseDTO> checkHealth() {
        boolean dbUp = checkDatabaseConnection();
        HealthResponseDTO health = HealthResponseDTO.builder()
                .status(dbUp ? "UP" : "DOWN")
                .database(dbUp ? "Oracle Database UP" : "Oracle Database DOWN")
                .timestamp(LocalDateTime.now())
                .build();

        if (dbUp) {
            return ResponseEntity.ok(health);
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(health);
        }
    }

    private boolean checkDatabaseConnection() {
        try (Connection conn = dataSource.getConnection()) {
            return conn.isValid(2);
        } catch (Exception e) {
            return false;
        }
    }
}