package edu.javeriana.process.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginDTO {
    @NotBlank @Email @Size(max = 150) private String email;
    @NotBlank @Size(min = 8, max = 100) private String password;
}
