package edu.javeriana.process.service;

import edu.javeriana.process.model.Process;
import edu.javeriana.process.repository.ProcessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProcessServiceImplTest {

    @Mock
    private ProcessRepository processRepository;

    @InjectMocks
    private ProcessServiceImpl processService;

    private Process process;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        process = new Process();
        process.setId(1L);
        process.setName("Proceso de prueba");
        process.setDescription("Descripción de prueba");
        process.setStatus("ACTIVO");
    }

    @Test
    void testCreate() {
        when(processRepository.save(process)).thenReturn(process);

        Process saved = processService.create(process);

        assertNotNull(saved);
        assertEquals("Proceso de prueba", saved.getName());
        verify(processRepository, times(1)).save(process);
    }

    @Test
    void testGetById_Success() {
        when(processRepository.findById(1L)).thenReturn(Optional.of(process));

        Process found = processService.getById(1L);

        assertNotNull(found);
        assertEquals("Proceso de prueba", found.getName());
        verify(processRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(processRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> processService.getById(99L));
        verify(processRepository, times(1)).findById(99L);
    }

    @Test
    void testGetAll() {
        when(processRepository.findAll()).thenReturn(Arrays.asList(process));

        List<Process> processes = processService.getAll();

        assertEquals(1, processes.size());
        assertEquals("Proceso de prueba", processes.get(0).getName());
        verify(processRepository, times(1)).findAll();
    }

    @Test
    void testUpdate_Success() {
        Process updated = new Process();
        updated.setName("Proceso actualizado");
        updated.setDescription("Descripción actualizada");
        updated.setStatus("INACTIVO");

        when(processRepository.findById(1L)).thenReturn(Optional.of(process));
        when(processRepository.save(any(Process.class))).thenReturn(updated);

        Process result = processService.update(1L, updated);

        assertNotNull(result);
        assertEquals("Proceso actualizado", result.getName());
        assertEquals("INACTIVO", result.getStatus());
        verify(processRepository, times(1)).save(any(Process.class));
    }

    @Test
    void testDelete_Success() {
        when(processRepository.existsById(1L)).thenReturn(true);
        doNothing().when(processRepository).deleteById(1L);

        processService.delete(1L);

        verify(processRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() {
        when(processRepository.existsById(99L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> processService.delete(99L));
        verify(processRepository, never()).deleteById(anyLong());
    }
}
