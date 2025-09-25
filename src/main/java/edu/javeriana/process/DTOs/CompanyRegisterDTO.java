package edu.javeriana.process.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CompanyRegisterDTO {
    @NotBlank @Size(max = 150) private String companyName;
    @NotBlank @Size(max = 50)  private String nit;
    @NotBlank @Email @Size(max = 150) private String adminEmail;
    @NotBlank @Size(max = 150) private String adminFullName;
    @NotBlank @Size(min = 8, max = 100) private String adminPassword;
}
