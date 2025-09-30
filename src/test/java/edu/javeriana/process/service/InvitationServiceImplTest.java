package edu.javeriana.process.service;

import edu.javeriana.process.model.Company;
import edu.javeriana.process.model.Invitation;
import edu.javeriana.process.model.Role;
import edu.javeriana.process.repository.InvitationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvitationServiceImplTest {

    @Mock
    private InvitationRepository invRepo;

    @InjectMocks
    private InvitationServiceImpl invitationService;

    @Test
    void testCreateInvitation_Success() {
        Company company = new Company();
        String email = "TEST@MAIL.COM";
        Role role = Role.ADMIN;
        int daysValid = 7;

        when(invRepo.save(any(Invitation.class)))
                .thenAnswer(inv -> inv.getArgument(0)); // Devuelve el mismo objeto que se guarda

        Invitation result = invitationService.createInvitation(company, email, role, daysValid);

        assertNotNull(result.getToken());
        assertEquals(email.toLowerCase(), result.getEmail());
        assertEquals(company, result.getCompany());
        assertEquals(role, result.getRole());
        assertTrue(result.getExpiresAt().isAfter(LocalDateTime.now()));

        verify(invRepo, times(1)).save(any(Invitation.class));
    }

    @Test
    void testGetByTokenOrThrow_Found() {
        String token = "abc-123";
        Invitation inv = new Invitation();
        inv.setToken(token);

        when(invRepo.findByToken(token)).thenReturn(Optional.of(inv));

        Invitation result = invitationService.getByTokenOrThrow(token);

        assertNotNull(result);
        assertEquals(token, result.getToken());
        verify(invRepo, times(1)).findByToken(token);
    }

    @Test
    void testGetByTokenOrThrow_NotFound() {
        String token = "not-found";

        when(invRepo.findByToken(token)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            invitationService.getByTokenOrThrow(token);
        });

        verify(invRepo, times(1)).findByToken(token);
    }

    @Test
    void testMarkAccepted_Success() {
        Invitation inv = new Invitation();
        inv.setAccepted(false);

        when(invRepo.save(inv)).thenReturn(inv);

        invitationService.markAccepted(inv);

        assertTrue(inv.isAccepted());
        verify(invRepo, times(1)).save(inv);
    }

    @Test
    void testCreate() {
        Company company;
        company = new Company();
        company.setId(1L);
        company.setName("Test Company");
        company.setNit("12345");
        company.setContactEmail("test@company.com");

        Invitation invitation;
        invitation = new Invitation();
        invitation.setId(1L);
        invitation.setEmail("test@email.com");
        invitation.setRole(Role.ADMIN);
        invitation.setCompany(company);
        invitation.setToken("abc123");
        invitation.setExpiresAt(LocalDateTime.now().plusDays(5));
        invitation.setAccepted(false);

        when(invRepo.save(invitation)).thenReturn(invitation);

        Invitation saved = invitationService.create(invitation);

        assertNotNull(saved);
        assertEquals("test@email.com", saved.getEmail());
        verify(invRepo, times(1)).save(invitation);
    }

    @Test
    void testGetById_Success() {
        Company company;
        company = new Company();
        company.setId(1L);
        company.setName("Test Company");
        company.setNit("12345");
        company.setContactEmail("test@company.com");

        Invitation invitation;
        invitation = new Invitation();
        invitation.setId(1L);
        invitation.setEmail("test@email.com");
        invitation.setRole(Role.ADMIN);
        invitation.setCompany(company);
        invitation.setToken("abc123");
        invitation.setExpiresAt(LocalDateTime.now().plusDays(5));
        invitation.setAccepted(false);

        when(invRepo.findById(1L)).thenReturn(Optional.of(invitation));

        Invitation found = invitationService.getById(1L);

        assertNotNull(found);
        assertEquals("abc123", found.getToken());
        verify(invRepo, times(1)).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(invRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> invitationService.getById(99L));
        verify(invRepo, times(1)).findById(99L);
    }

    @Test
    void testGetAll() {
        Company company;
        company = new Company();
        company.setId(1L);
        company.setName("Test Company");
        company.setNit("12345");
        company.setContactEmail("test@company.com");

        Invitation invitation;
        invitation = new Invitation();
        invitation.setId(1L);
        invitation.setEmail("test@email.com");
        invitation.setRole(Role.ADMIN);
        invitation.setCompany(company);
        invitation.setToken("abc123");
        invitation.setExpiresAt(LocalDateTime.now().plusDays(5));
        invitation.setAccepted(false);

        when(invRepo.findAll()).thenReturn(Arrays.asList(invitation));

        List<Invitation> invitations = invitationService.getAll();

        assertEquals(1, invitations.size());
        verify(invRepo, times(1)).findAll();
    }

    @Test
    void testUpdate_Success() {
        Company company;
        company = new Company();
        company.setId(1L);
        company.setName("Test Company");
        company.setNit("12345");
        company.setContactEmail("test@company.com");

        Invitation invitation;
        invitation = new Invitation();
        invitation.setId(1L);
        invitation.setEmail("test@email.com");
        invitation.setRole(Role.ADMIN);
        invitation.setCompany(company);
        invitation.setToken("abc123");
        invitation.setExpiresAt(LocalDateTime.now().plusDays(5));
        invitation.setAccepted(false);

        Invitation updated = new Invitation();
        updated.setEmail("new@email.com");
        updated.setRole(Role.EDITOR);
        updated.setCompany(company);
        updated.setToken("newToken");
        updated.setExpiresAt(LocalDateTime.now().plusDays(10));
        updated.setAccepted(true);

        when(invRepo.findById(1L)).thenReturn(Optional.of(invitation));
        when(invRepo.save(any(Invitation.class))).thenReturn(updated);

        Invitation result = invitationService.update(1L, updated);

        assertNotNull(result);
        assertEquals("new@email.com", result.getEmail());
        assertTrue(result.isAccepted());
        verify(invRepo, times(1)).save(any(Invitation.class));
    }

    @Test
    void testDelete_Success() {
        when(invRepo.existsById(1L)).thenReturn(true);
        doNothing().when(invRepo).deleteById(1L);

        invitationService.delete(1L);

        verify(invRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() {
        when(invRepo.existsById(99L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> invitationService.delete(99L));
        verify(invRepo, never()).deleteById(anyLong());
    }

    @Test
    void testGetByCompanyId() {
        Company company;
        company = new Company();
        company.setId(1L);
        company.setName("Test Company");
        company.setNit("12345");
        company.setContactEmail("test@company.com");

        Invitation invitation;
        invitation = new Invitation();
        invitation.setId(1L);
        invitation.setEmail("test@email.com");
        invitation.setRole(Role.ADMIN);
        invitation.setCompany(company);
        invitation.setToken("abc123");
        invitation.setExpiresAt(LocalDateTime.now().plusDays(5));
        invitation.setAccepted(false);

        when(invRepo.findAllByCompanyId(1L)).thenReturn(Arrays.asList(invitation));

        List<Invitation> invitations = invitationService.getByCompanyId(1L);

        assertEquals(1, invitations.size());
        verify(invRepo, times(1)).findAllByCompanyId(1L);
    }
}
