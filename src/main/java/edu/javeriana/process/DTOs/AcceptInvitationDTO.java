package edu.javeriana.process.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AcceptInvitationDTO {
    @NotBlank private String token;
    @NotBlank @Size(max = 150) private String fullName;
    @NotBlank @Size(min = 8, max = 100) private String password;
}
