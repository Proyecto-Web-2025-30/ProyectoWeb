package edu.javeriana.process.service;

import java.util.List;
import edu.javeriana.process.model.*;

public interface UserService {
    AppUser createFromInvitation(Invitation inv, String fullName, String rawPassword);
    List<AppUser> listByCompany(Company company);
    AppUser getByEmailOrThrow(String email);
}
