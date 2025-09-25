package edu.javeriana.process.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Table(name = "company", uniqueConstraints = {
        @UniqueConstraint(name="uk_company_nit", columnNames = "nit")
})
@Data @NoArgsConstructor @AllArgsConstructor
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 150)
    private String name;

    @NotBlank @Size(max = 50)
    private String nit;

    @NotBlank @Size(max = 150)
    private String contactEmail;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
