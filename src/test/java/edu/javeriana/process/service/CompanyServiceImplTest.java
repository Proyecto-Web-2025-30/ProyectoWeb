package edu.javeriana.process.service;

import edu.javeriana.process.model.*;
import edu.javeriana.process.repository.CompanyRepository;
import edu.javeriana.process.repository.UserRepository;
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
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    void testRegisterCompanyAndAdmin_Success() {
        // Arrange
        String name = "MiEmpresa";
        String nit = "12345";
        String adminEmail = "admin@empresa.com";
        String adminFullName = "Admin Test";
        String adminPassword = "secret";

        when(companyRepo.findByNit(nit)).thenReturn(Optional.empty());
        when(encoder.encode(adminPassword)).thenReturn("hashed");

        // Act
        Company result = companyService.registerCompanyAndAdmin(
                name, nit, adminEmail, adminFullName, adminPassword
        );

        // Assert
        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(nit, result.getNit());
        assertEquals(adminEmail.toLowerCase(), result.getContactEmail());

        // Verificar que se guardaron la empresa y el usuario
        verify(companyRepo, times(1)).save(any(Company.class));
        verify(userRepo, times(1)).save(any(AppUser.class));
    }

    @Test
    void testRegisterCompanyAndAdmin_FailsIfNitExists() {
        String nit = "12345";
        when(companyRepo.findByNit(nit)).thenReturn(Optional.of(new Company()));

        assertThrows(IllegalArgumentException.class, () ->
                companyService.registerCompanyAndAdmin("MiEmpresa", nit, "admin@empresa.com", "Admin Test", "secret")
        );

        verify(companyRepo, never()).save(any());
        verify(userRepo, never()).save(any());
    }

    @Test
    void testGetById_Success() {
        Long id = 1L;
        Company company = new Company();
        company.setId(id);

        when(companyRepo.findById(id)).thenReturn(Optional.of(company));

        Company result = companyService.getById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(companyRepo, times(1)).findById(id);
    }

    @Test
    void testGetById_NotFound() {
        Long id = 1L;
        when(companyRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> companyService.getById(id));
    }

    @Test
    void testCreate() {
        Company company;
        company = new Company();
        company.setId(1L);
        company.setName("Mi Empresa");
        company.setNit("123456789");
        company.setContactEmail("contacto@miempresa.com");
        company.setCreatedAt(LocalDateTime.now());

        when(companyRepo.save(any(Company.class))).thenReturn(company);

        Company created = companyService.create(company);

        assertNotNull(created);
        assertEquals("Mi Empresa", created.getName());
        verify(companyRepo, times(1)).save(company);
    }
    @Test
    void testGetAll() {
        Company company;
        company = new Company();
        company.setId(1L);
        company.setName("Mi Empresa");
        company.setNit("123456789");
        company.setContactEmail("contacto@miempresa.com");
        company.setCreatedAt(LocalDateTime.now());

        when(companyRepo.findAll()).thenReturn(List.of(company));

        List<Company> result = companyService.getAll();

        assertEquals(1, result.size());
        assertEquals("Mi Empresa", result.get(0).getName());
        verify(companyRepo, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        Company company;
        company = new Company();
        company.setId(1L);
        company.setName("Mi Empresa");
        company.setNit("123456789");
        company.setContactEmail("contacto@miempresa.com");
        company.setCreatedAt(LocalDateTime.now());

        Company updated = new Company();
        updated.setName("Empresa Modificada");
        updated.setNit("987654321");
        updated.setContactEmail("nuevo@empresa.com");
        updated.setCreatedAt(company.getCreatedAt());

        when(companyRepo.findById(1L)).thenReturn(Optional.of(company));
        when(companyRepo.save(any(Company.class))).thenReturn(updated);

        Company result = companyService.update(1L, updated);

        assertNotNull(result);
        assertEquals("Empresa Modificada", result.getName());
        assertEquals("987654321", result.getNit());
        verify(companyRepo, times(1)).save(company);
    }

    @Test
    void testDeleteExisting() {
        when(companyRepo.existsById(1L)).thenReturn(true);
        doNothing().when(companyRepo).deleteById(1L);

        companyService.delete(1L);

        verify(companyRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotExisting() {
        when(companyRepo.existsById(99L)).thenReturn(false);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> companyService.delete(99L));

        assertEquals("Empresa no existe", ex.getMessage());
    }
}