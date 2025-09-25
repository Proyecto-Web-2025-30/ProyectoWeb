package edu.javeriana.process.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Opción B: delegating (usa {bcrypt} por defecto)
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",                 // raíz
                                "/login",            // página de login (GET)
                                "/error",            // evita loops cuando hay errores
                                "/register-company", // registro público
                                "/users/accept", "/users/accept/**", // aceptar invitaciones
                                "/css/**", "/js/**", "/images/**"    // estáticos
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        // false => usa la "saved request" si existe; si no, /dashboard
                        .defaultSuccessUrl("/dashboard", false)
                        .failureUrl("/login?error")
                        // opcional: asegura que el POST de login es /login (por defecto ya lo es)
                        .loginProcessingUrl("/login")
                )
                .logout(logout -> logout
                        // Spring Security exige POST para /logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        // CSRF habilitado por defecto: añade el hidden en todos los formularios POST
        return http.build();
    }
}

