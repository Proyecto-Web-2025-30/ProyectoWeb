package edu.javeriana.process.service;

import edu.javeriana.process.model.Company;

public interface CompanyService {
    Company registerCompanyAndAdmin(String name, String nit, String adminEmail, String adminFullName, String adminPassword);
    Company getById(Long id);
}
