package edu.javeriana.process.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "edge")
public class Edge {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 255, message = "Nombre muy largo")
    private String name;

    @NotBlank
    private String description;
    
    @NotBlank
    @Size(max = 45, message = "Tipo muy largo")
    private String type;

    // Constructors
    public Edge() {}

    public Edge(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

