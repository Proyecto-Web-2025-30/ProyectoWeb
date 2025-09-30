package edu.javeriana.process.service;

import edu.javeriana.process.model.*;

import java.util.List;

public interface InvitationService {
    Invitation createInvitation(Company company, String email, Role role, int daysValid);
    Invitation getByTokenOrThrow(String token);
    void markAccepted(Invitation inv);

    Invitation create(Invitation invitation);         //CREATE
    Invitation getById(Long id);                      //READ
    List<Invitation> getAll();                        //READ ALL
    Invitation update(Long id, Invitation invitation);//UPDATE
    void delete(Long id);                             //DELETE
    List<Invitation> getByCompanyId(Long companyId);
}
