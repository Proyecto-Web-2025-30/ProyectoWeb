package edu.javeriana.process.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import edu.javeriana.process.model.*;
import edu.javeriana.process.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @Transactional
    @Override
    public AppUser createFromInvitation(Invitation inv, String fullName, String rawPassword) {
        AppUser u = new AppUser();
        u.setEmail(inv.getEmail().toLowerCase());
        u.setFullName(fullName);
        u.setPasswordHash(encoder.encode(rawPassword));
        u.setRole(inv.getRole());
        u.setCompany(inv.getCompany());
        return userRepo.save(u);
    }

    @Override
    public List<AppUser> listByCompany(Company company) {
        return userRepo.findAllByCompany(company);
    }

    @Override
    public AppUser getByEmailOrThrow(String email) {
        return userRepo.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no existe"));
    }
}
