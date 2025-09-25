package edu.javeriana.process.controllers;

import edu.javeriana.process.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String root(@AuthenticationPrincipal UserPrincipal up) {
        return (up != null) ? "redirect:/dashboard" : "redirect:/login";
    }
}

