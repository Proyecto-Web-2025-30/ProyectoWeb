package edu.javeriana.process.controllers;

import edu.javeriana.process.DTOs.InviteUserDTO;
import edu.javeriana.process.DTOs.AcceptInvitationDTO;
import edu.javeriana.process.model.*;
import edu.javeriana.process.security.UserPrincipal;
import edu.javeriana.process.service.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final InvitationService invitationService;
    private final UserService userService;
    private final CompanyService companyService;

    // Solo ADMIN puede invitar
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/invite")
    public String inviteForm(@AuthenticationPrincipal UserPrincipal up, Model model){
        if(!model.containsAttribute("form")){
            InviteUserDTO form = new InviteUserDTO();
            form.setCompanyId(up.getUser().getCompany().getId());
            model.addAttribute("form", form);
        }
        model.addAttribute("roles", Role.values());
        return "user/InviteUser";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/invite")
    public String invite(@AuthenticationPrincipal UserPrincipal up,
                         @Valid @ModelAttribute("form") InviteUserDTO form,
                         BindingResult br,
                         RedirectAttributes ra){
        if (br.hasErrors()){
            ra.addFlashAttribute("org.springframework.validation.BindingResult.form", br);
            ra.addFlashAttribute("form", form);
            return "redirect:/users/invite";
        }

        Long currentCompanyId = up.getUser().getCompany().getId();
        if (!currentCompanyId.equals(form.getCompanyId())){
            ra.addFlashAttribute("error", "Empresa inválida para la invitación.");
            return "redirect:/users/invite";
        }

        Company company = companyService.getById(currentCompanyId);
        Invitation inv = invitationService.createInvitation(company, form.getEmail(), form.getRole(), 7);

        // TODO: enviar correo con link: /users/accept?token=...
        ra.addFlashAttribute("success", "Invitación creada. Token: " + inv.getToken());
        return "redirect:/users/invite";
    }

    // Cualquiera con token puede aceptar
    @GetMapping("/accept")
    public String acceptForm(@RequestParam("token") String token, Model model){
        if(!model.containsAttribute("form")){
            AcceptInvitationDTO dto = new AcceptInvitationDTO();
            dto.setToken(token);
            model.addAttribute("form", dto);
        }
        return "user/AcceptInvitation";
    }

    @PostMapping("/accept")
    public String accept(@Valid @ModelAttribute("form") AcceptInvitationDTO form,
                         BindingResult br,
                         RedirectAttributes ra){
        if (br.hasErrors()){
            ra.addFlashAttribute("org.springframework.validation.BindingResult.form", br);
            ra.addFlashAttribute("form", form);
            return "redirect:/users/accept?token=" + form.getToken();
        }

        try {
            Invitation inv = invitationService.getByTokenOrThrow(form.getToken());
            if (inv.isAccepted() || inv.getExpiresAt().isBefore(java.time.LocalDateTime.now())){
                ra.addFlashAttribute("error", "Invitación inválida o expirada.");
                return "redirect:/login";
            }

            userService.createFromInvitation(inv, form.getFullName(), form.getPassword());
            invitationService.markAccepted(inv);
            ra.addFlashAttribute("success", "Cuenta creada. Ya puedes iniciar sesión.");
            return "redirect:/login";
        } catch (IllegalArgumentException ex){
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/login";
        }
    }
}
