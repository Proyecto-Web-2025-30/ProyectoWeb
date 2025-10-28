package edu.javeriana.process.DTOs;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private String name;

    public JwtResponse(String token, String email, String name) {
        this.token = token;
        this.email = email;
        this.name = name;
    }
}

