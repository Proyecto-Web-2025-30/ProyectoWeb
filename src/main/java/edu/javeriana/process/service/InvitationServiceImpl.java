package edu.javeriana.process.service;

import java.time.LocalDateTime;
import java.util.List;
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

    @Override
    public Invitation create(Invitation invitation) {
        return invRepo.save(invitation);
    }

    @Override
    public Invitation getById(Long id) {
        return invRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invitación no encontrada"));
    }

    @Override
    public List<Invitation> getAll() {
        return invRepo.findAll();
    }

    @Override
    public Invitation update(Long id, Invitation invitation) {
        Invitation existing = getById(id);
        existing.setEmail(invitation.getEmail());
        existing.setRole(invitation.getRole());
        existing.setCompany(invitation.getCompany());
        existing.setToken(invitation.getToken());
        existing.setExpiresAt(invitation.getExpiresAt());
        existing.setAccepted(invitation.isAccepted());
        return invRepo.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!invRepo.existsById(id)) {
            throw new IllegalArgumentException("Invitación no existe");
        }
        invRepo.deleteById(id);
    }

    @Override
    public List<Invitation> getByCompanyId(Long companyId) {
        return invRepo.findAllByCompanyId(companyId);
    }
}
