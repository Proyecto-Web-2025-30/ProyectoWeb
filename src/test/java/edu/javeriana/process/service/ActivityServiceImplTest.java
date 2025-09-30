package edu.javeriana.process.service;

import edu.javeriana.process.model.Activity;
import edu.javeriana.process.model.Process;
import edu.javeriana.process.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityServiceImplTest {

    @Mock
    private ActivityRepository activityRepo;

    @InjectMocks
    private ActivityServiceImpl activityService;

    private Activity activity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Process process = new Process(); // entidad relacionada
        process.setId(1L);

        activity = new Activity();
        activity.setId(1L);
        activity.setName("Tarea 1");
        activity.setX(10.0);
        activity.setY(20.0);
        activity.setDescription("Descripción inicial");
        activity.setWidth(100.0);
        activity.setHeight(50.0);
        activity.setStatus("ACTIVO");
        activity.setProcess(process);
    }

    @Test
    void testCreate() {
        when(activityRepo.save(any(Activity.class))).thenReturn(activity);

        Activity created = activityService.create(activity);

        assertNotNull(created);
        assertEquals("Tarea 1", created.getName());
        verify(activityRepo, times(1)).save(activity);
    }

    @Test
    void testGetByIdFound() {
        when(activityRepo.findById(1L)).thenReturn(Optional.of(activity));

        Activity result = activityService.getById(1L);

        assertNotNull(result);
        assertEquals("Tarea 1", result.getName());
        verify(activityRepo, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(activityRepo.findById(99L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> activityService.getById(99L));

        assertEquals("Actividad no encontrada", ex.getMessage());
    }

    @Test
    void testGetAll() {
        when(activityRepo.findAll()).thenReturn(List.of(activity));

        List<Activity> result = activityService.getAll();

        assertEquals(1, result.size());
        assertEquals("Tarea 1", result.get(0).getName());
        verify(activityRepo, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        Activity updated = new Activity();
        updated.setName("Tarea Modificada");
        updated.setX(15.0);
        updated.setY(25.0);
        updated.setDescription("Nueva descripción");
        updated.setWidth(120.0);
        updated.setHeight(60.0);
        updated.setStatus("INACTIVO");
        updated.setProcess(activity.getProcess());

        when(activityRepo.findById(1L)).thenReturn(Optional.of(activity));
        when(activityRepo.save(any(Activity.class))).thenReturn(updated);

        Activity result = activityService.update(1L, updated);

        assertNotNull(result);
        assertEquals("Tarea Modificada", result.getName());
        assertEquals("INACTIVO", result.getStatus());
        verify(activityRepo, times(1)).save(activity);
    }

    @Test
    void testDeleteExisting() {
        when(activityRepo.existsById(1L)).thenReturn(true);
        doNothing().when(activityRepo).deleteById(1L);

        activityService.delete(1L);

        verify(activityRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotExisting() {
        when(activityRepo.existsById(99L)).thenReturn(false);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> activityService.delete(99L));

        assertEquals("Actividad no existe", ex.getMessage());
    }
}
