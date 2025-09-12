package edu.javeriana.process.DTOs;

import edu.javeriana.process.model.Edge;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EdgeDTO {
    private Long id;

    @NotBlank
    @Size(max = 255, message = "Nombre muy largo")
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    @Size(max = 45, message = "Tipo muy largo")
    private String type;

    public static EdgeDTO fromEntity(Edge e) {
        return new EdgeDTO(e.getId(), e.getName(), e.getDescription(), e.getType());
    }

    /** Usa el all-args de Edge: (Long id, String name, String description, String type) */
    public Edge toEntity() {
        return new Edge(id, name, description, type); // id puede ser null al crear
    }
}
