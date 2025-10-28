package edu.javeriana.process.DTOs;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ApiErrorResponse {
    private String message;
    private int status;
}

