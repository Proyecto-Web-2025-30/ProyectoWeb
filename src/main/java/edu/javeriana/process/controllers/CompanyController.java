package edu.javeriana.process.controllers;

import edu.javeriana.process.DTOs.CompanyRegisterDTO;
import edu.javeriana.process.model.Company;
import edu.javeriana.process.service.CompanyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/register-company")
    public String showRegisterForm(Model model){
        if(!model.containsAttribute("form")){
            model.addAttribute("form", new CompanyRegisterDTO());
        }
        return "company/RegisterCompany";
    }

    @PostMapping("/register-company")
    public String registerCompany(
            @Valid @ModelAttribute("form") CompanyRegisterDTO form,
            BindingResult br,
            RedirectAttributes ra) {

        if (br.hasErrors()){
            ra.addFlashAttribute("org.springframework.validation.BindingResult.form", br);
            ra.addFlashAttribute("form", form);
            return "redirect:/register-company";
        }

        try {
            Company c = companyService.registerCompanyAndAdmin(
                    form.getCompanyName(), form.getNit(), form.getAdminEmail(),
                    form.getAdminFullName(), form.getAdminPassword()
            );
            ra.addFlashAttribute("success", "Empresa creada: " + c.getName() + ". Ya puedes iniciar sesión.");
            return "redirect:/login";
        } catch (IllegalArgumentException ex){
            ra.addFlashAttribute("error", ex.getMessage());
            ra.addFlashAttribute("form", form);
            return "redirect:/register-company";
        }
    }
}

