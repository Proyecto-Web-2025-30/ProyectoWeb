package edu.javeriana.process.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;
import edu.javeriana.process.model.Role;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InviteUserDTO {
    @NotNull private Long companyId; // seguridad: validaremos contra la sesión
    @NotBlank @Email @Size(max = 150) private String email;
    @NotNull private Role role;
}
