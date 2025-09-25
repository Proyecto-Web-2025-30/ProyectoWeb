package edu.javeriana.process.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import edu.javeriana.process.model.*;
import edu.javeriana.process.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @Transactional
    @Override
    public Company registerCompanyAndAdmin(String name, String nit, String adminEmail, String adminFullName, String adminPassword) {
        companyRepo.findByNit(nit).ifPresent(c -> { throw new IllegalArgumentException("El NIT ya existe"); });

        Company company = new Company();
        company.setName(name);
        company.setNit(nit);
        company.setContactEmail(adminEmail.toLowerCase());
        companyRepo.save(company);

        AppUser admin = new AppUser();
        admin.setEmail(adminEmail.toLowerCase());
        admin.setFullName(adminFullName);
        admin.setPasswordHash(encoder.encode(adminPassword));
        admin.setRole(Role.ADMIN);
        admin.setCompany(company);
        userRepo.save(admin);

        return company;
    }

    @Override
    public Company getById(Long id) {
        return companyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no existe"));
    }
}


