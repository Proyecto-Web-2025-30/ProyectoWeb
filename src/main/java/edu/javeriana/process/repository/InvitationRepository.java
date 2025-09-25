package edu.javeriana.process.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.javeriana.process.model.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findByToken(String token);
}

