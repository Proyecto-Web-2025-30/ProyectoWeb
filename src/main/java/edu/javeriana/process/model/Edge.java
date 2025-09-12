package edu.javeriana.process.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "edge")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255, message = "Nombre muy largo")
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    @Size(max = 45, message = "Tipo muy largo")
    private String type;
}
