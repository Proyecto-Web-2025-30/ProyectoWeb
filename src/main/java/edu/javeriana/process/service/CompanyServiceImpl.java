package edu.javeriana.process.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import edu.javeriana.process.model.*;
import edu.javeriana.process.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

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

    @Transactional
    @Override
    public Company create(Company company) {
        return companyRepo.save(company);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Company> getAll() {
        return companyRepo.findAll();
    }

    @Transactional
    @Override
    public Company update(Long id, Company updated) {
        Company existing = getById(id); // Lanza excepción si no existe
        existing.setName(updated.getName());
        existing.setNit(updated.getNit());
        existing.setContactEmail(updated.getContactEmail());
        return companyRepo.save(existing);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!companyRepo.existsById(id)) {
            throw new IllegalArgumentException("Empresa no existe");
        }
        companyRepo.deleteById(id);
    }
}


