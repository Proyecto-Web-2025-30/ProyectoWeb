package edu.javeriana.process.service;

import edu.javeriana.process.model.*;

public interface InvitationService {
    Invitation createInvitation(Company company, String email, Role role, int daysValid);
    Invitation getByTokenOrThrow(String token);
    void markAccepted(Invitation inv);
}
