package edu.javeriana.process.service;

import java.util.List;
import edu.javeriana.process.model.*;

public interface UserService {
    AppUser createFromInvitation(Invitation inv, String fullName, String rawPassword);
    List<AppUser> listByCompany(Company company);
    AppUser getByEmailOrThrow(String email);

    AppUser create(AppUser user);         //CREATE
    AppUser getById(Long id);             //READ
    List<AppUser> getAll();               //READ ALL
    AppUser update(Long id, AppUser user);//UPDATE
    void delete(Long id);                 //DELETE
}
