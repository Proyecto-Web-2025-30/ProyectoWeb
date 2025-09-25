package edu.javeriana.process.service;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import edu.javeriana.process.model.*;
import edu.javeriana.process.repository.*;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invRepo;

    @Transactional
    @Override
    public Invitation createInvitation(Company company, String email, Role role, int daysValid) {
        Invitation inv = new Invitation();
        inv.setCompany(company);
        inv.setEmail(email.toLowerCase());
        inv.setRole(role);
        inv.setToken(UUID.randomUUID().toString());
        inv.setExpiresAt(LocalDateTime.now().plusDays(daysValid));
        return invRepo.save(inv);
    }

    @Transactional(readOnly = true)
    @Override
    public Invitation getByTokenOrThrow(String token) {
        return invRepo.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invitación no existe"));
    }

    @Transactional
    @Override
    public void markAccepted(Invitation inv) {
        inv.setAccepted(true);
        invRepo.save(inv);
    }
}
