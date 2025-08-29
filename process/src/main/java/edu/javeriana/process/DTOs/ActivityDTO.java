package edu.javeriana.process.DTOs;

import edu.javeriana.process.model.Activity;
import edu.javeriana.process.model.Process;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {
    private Long id;

    @NotBlank
    @Size(max = 45, message = "Nombre muy largo")
    private String name;

    @NotNull private Double x;
    @NotNull private Double y;

    @NotBlank
    @Size(max = 45, message = "Descripción muy larga")
    private String description;

    @NotNull private Double width;
    @NotNull private Double height;

    @NotBlank
    @Size(max = 45, message = "Estado muy largo")
    private String status;

    // Exponemos solo el id del proceso
    @NotNull
    private Long processId;

    public static ActivityDTO from(Activity a) {
        return new ActivityDTO(
            a.getId(),
            a.getName(),
            a.getX(),
            a.getY(),
            a.getDescription(),
            a.getWidth(),
            a.getHeight(),
            a.getStatus(),
            (a.getProcess() != null ? a.getProcess().getId() : null)
        );
    }

    public Activity toEntity(Process process) {
        // Usa el ctor Activity(String name, Double x, Double y, String description, Double width, Double height, String status, Process process)
        return new Activity(id, name, x, y, description, width, height, status, process);
    }
}
