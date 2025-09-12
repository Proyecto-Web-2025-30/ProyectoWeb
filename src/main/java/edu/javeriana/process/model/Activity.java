package edu.javeriana.process.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "activity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "process_id")
    private Process process;
}

