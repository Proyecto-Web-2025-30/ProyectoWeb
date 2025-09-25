package edu.javeriana.process.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.javeriana.process.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByNit(String nit);
}
