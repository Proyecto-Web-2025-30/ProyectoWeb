package edu.javeriana.process.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.javeriana.process.model.AppUser;
import edu.javeriana.process.model.Company;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    List<AppUser> findAllByCompany(Company company);
}
