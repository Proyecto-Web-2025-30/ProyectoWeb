package edu.javeriana.process.controllers.api;

import edu.javeriana.process.DTOs.*;
import edu.javeriana.process.model.Company;
import edu.javeriana.process.model.AppUser;
import edu.javeriana.process.security.JwtUtils;
import edu.javeriana.process.security.UserPrincipal;
import edu.javeriana.process.service.CompanyService;
import edu.javeriana.process.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CompanyService companyService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            AppUser user = userService.getByEmail(userPrincipal.getUsername());

            JwtResponse response = new JwtResponse(
                    jwt,
                    user.getEmail(),
                    user.getFullName()
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiErrorResponse("Credenciales inválidas", 401));
        }
    }

    @PostMapping("/register-company")
    public ResponseEntity<?> registerCompany(@Valid @RequestBody CompanyRegisterDTO registerRequest) {
        try {
            Company company = companyService.registerCompanyAndAdmin(
                    registerRequest.getCompanyName(),
                    registerRequest.getNit(),
                    registerRequest.getAdminEmail(),
                    registerRequest.getAdminFullName(),
                    registerRequest.getAdminPassword()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("companyId", company.getId());
            response.put("message", "Empresa creada exitosamente");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("Error creando empresa", 500));
        }
    }
}

