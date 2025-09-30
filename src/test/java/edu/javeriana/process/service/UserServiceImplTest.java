package edu.javeriana.process.service;

import edu.javeriana.process.model.AppUser;
import edu.javeriana.process.model.Company;
import edu.javeriana.process.model.Invitation;
import edu.javeriana.process.model.Role;
import edu.javeriana.process.repository.UserRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    // Prueba de invitacion para los 3 roles
    @ParameterizedTest
    @EnumSource(Role.class)
    void testCreateFromInvitation_AllRoles(Role role) {
        Company company = new Company();
        Invitation inv = new Invitation();
        inv.setEmail("Test@Mail.com");
        inv.setRole(role);
        inv.setCompany(company);

        String fullName = "Juan Pérez";
        String rawPassword = "secret";

        when(encoder.encode(rawPassword)).thenReturn("hashed");
        when(userRepo.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AppUser result = userService.createFromInvitation(inv, fullName, rawPassword);

        assertNotNull(result);
        assertEquals(inv.getEmail().toLowerCase(), result.getEmail());
        assertEquals(fullName, result.getFullName());
        assertEquals("hashed", result.getPasswordHash());
        assertEquals(role, result.getRole());  // Verifica cada rol
        assertEquals(company, result.getCompany());

        verify(userRepo, times(1)).save(any(AppUser.class));
    }

    @Test
    void testListByCompany_Success() {
        Company company = new Company();
        AppUser u1 = new AppUser();
        AppUser u2 = new AppUser();
        List<AppUser> users = List.of(u1, u2);

        when(userRepo.findAllByCompany(company)).thenReturn(users);

        List<AppUser> result = userService.listByCompany(company);

        assertEquals(2, result.size());
        assertTrue(result.contains(u1));
        assertTrue(result.contains(u2));

        verify(userRepo, times(1)).findAllByCompany(company);
    }

    @Test
    void testGetByEmailOrThrow_Found() {
        String email = "test@mail.com";
        AppUser user = new AppUser();
        user.setEmail(email);

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        AppUser result = userService.getByEmailOrThrow(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepo, times(1)).findByEmail(email);
    }

    @Test
    void testGetByEmailOrThrow_NotFound() {
        String email = "missing@mail.com";
        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.getByEmailOrThrow(email));

        verify(userRepo, times(1)).findByEmail(email);
    }

    @Test
    void create_ShouldEncodePasswordAndSaveUser() {
        Company company;
        company = new Company();
        company.setId(1L);
        company.setName("Mi Empresa");
        company.setNit("123456789");
        company.setContactEmail("contacto@miempresa.com");
        company.setCreatedAt(LocalDateTime.now());

        AppUser user;
        user = new AppUser(
                1L,
                "test@example.com",
                "password123",
                "Test User",
                Role.ADMIN,
                company,
                true,
                null
        );

        when(encoder.encode(any())).thenReturn("encodedPass");
        when(userRepo.save(any())).thenReturn(user);

        AppUser saved = userService.create(user);

        assertNotNull(saved);
        verify(encoder).encode("password123");
        verify(userRepo).save(any(AppUser.class));
    }

    @Test
    void getById_ShouldReturnUser_WhenExists() {
        Company company;
        company = new Company();
        company.setId(1L);
        company.setName("Mi Empresa");
        company.setNit("123456789");
        company.setContactEmail("contacto@miempresa.com");
        company.setCreatedAt(LocalDateTime.now());

        AppUser user;
        user = new AppUser(
                1L,
                "test@example.com",
                "password123",
                "Test User",
                Role.ADMIN,
                company,
                true,
                null
        );

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        AppUser found = userService.getById(1L);

        assertEquals("test@example.com", found.getEmail());
        verify(userRepo).findById(1L);
    }

    @Test
    void getById_ShouldThrow_WhenNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.getById(1L));
    }

    @Test
    void update_ShouldModifyUserData() {
        Company company;
        company = new Company();
        company.setId(1L);
        company.setName("Mi Empresa");
        company.setNit("123456789");
        company.setContactEmail("contacto@miempresa.com");
        company.setCreatedAt(LocalDateTime.now());

        AppUser user;
        user = new AppUser(
                1L,
                "test@example.com",
                "password123",
                "Test User",
                Role.ADMIN,
                company,
                true,
                null
        );

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(userRepo.save(any())).thenReturn(user);
        when(encoder.encode(any())).thenReturn("newEncoded");

        AppUser updatedUser = new AppUser(null, "new@example.com", "newPass", "New Name",
                Role.ADMIN, company, true, null);

        AppUser result = userService.update(1L, updatedUser);

        assertEquals("new@example.com", result.getEmail());
        assertEquals("New Name", result.getFullName());
        verify(encoder).encode("newPass");
    }

    @Test
    void delete_ShouldCallRepository() {
        when(userRepo.existsById(1L)).thenReturn(true);
        doNothing().when(userRepo).deleteById(1L);

        userService.delete(1L);

        verify(userRepo).deleteById(1L);
    }
}
