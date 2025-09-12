package edu.javeriana.process.DTOs;

import edu.javeriana.process.model.Process;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProcessDTO {
    private Long id;

    @NotBlank
    @Size(max = 255, message = "Nombre muy largo")
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    @Size(max = 45, message = "Estado muy largo")
    private String status;

    public static ProcessDTO from(Process p) {
        return new ProcessDTO(
            p.getId(),
            p.getName(),
            p.getDescription(),
            p.getStatus()
        );
    }

    public Process toEntity() {
        // Usa el constructor de Process(String name, String description, String status)
        return new Process(id, name, description, status);
    }
}
