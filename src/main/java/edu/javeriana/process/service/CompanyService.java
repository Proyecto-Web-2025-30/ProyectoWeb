package edu.javeriana.process.service;

import edu.javeriana.process.model.Company;
import java.util.List;

public interface CompanyService {
    Company registerCompanyAndAdmin(String name, String nit, String adminEmail, String adminFullName, String adminPassword);
    Company create(Company company);          // CREATE
    Company getById(Long id);                 // READ
    List<Company> getAll();                   // READ ALL
    Company update(Long id, Company company); // UPDATE
    void delete(Long id);                     // DELETE
}
