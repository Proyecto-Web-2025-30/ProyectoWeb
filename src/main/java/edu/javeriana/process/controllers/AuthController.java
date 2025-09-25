package edu.javeriana.process.controllers;

import edu.javeriana.process.DTOs.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(Model model){
        if(!model.containsAttribute("login")){
            model.addAttribute("login", new LoginDTO());
        }
        return "auth/Login";
    }
    // El POST lo maneja Spring Security con formLogin
}
