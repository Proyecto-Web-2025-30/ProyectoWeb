package edu.javeriana.process.service;

import edu.javeriana.process.model.Edge;
import edu.javeriana.process.repository.EdgeRepository;
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

class EdgeServiceImplTest {

    @Mock
    private EdgeRepository edgeRepository;

    @InjectMocks
    private EdgeServiceImpl edgeService;

    private Edge edge;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        edge = new Edge(1L, "Arista1", "Descripción de prueba", "TipoA");
    }

    @Test
    void testCreateEdge() {
        when(edgeRepository.save(edge)).thenReturn(edge);

        Edge created = edgeService.create(edge);

        assertNotNull(created);
        assertEquals("Arista1", created.getName());
        verify(edgeRepository, times(1)).save(edge);
    }

    @Test
    void testGetById_Found() {
        when(edgeRepository.findById(1L)).thenReturn(Optional.of(edge));

        Edge found = edgeService.getById(1L);

        assertNotNull(found);
        assertEquals("Arista1", found.getName());
        verify(edgeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(edgeRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> edgeService.getById(1L));

        assertEquals("Arista no encontrada", exception.getMessage());
        verify(edgeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAll() {
        List<Edge> edges = Arrays.asList(edge, new Edge(2L, "Arista2", "Otra desc", "TipoB"));
        when(edgeRepository.findAll()).thenReturn(edges);

        List<Edge> result = edgeService.getAll();

        assertEquals(2, result.size());
        verify(edgeRepository, times(1)).findAll();
    }

    @Test
    void testUpdateEdge() {
        Edge updated = new Edge(1L, "AristaModificada", "Nueva desc", "TipoX");

        when(edgeRepository.findById(1L)).thenReturn(Optional.of(edge));
        when(edgeRepository.save(any(Edge.class))).thenReturn(updated);

        Edge result = edgeService.update(1L, updated);

        assertEquals("AristaModificada", result.getName());
        assertEquals("Nueva desc", result.getDescription());
        verify(edgeRepository, times(1)).findById(1L);
        verify(edgeRepository, times(1)).save(edge);
    }

    @Test
    void testDeleteEdge_Success() {
        when(edgeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(edgeRepository).deleteById(1L);

        edgeService.delete(1L);

        verify(edgeRepository, times(1)).existsById(1L);
        verify(edgeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteEdge_NotFound() {
        when(edgeRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> edgeService.delete(1L));

        assertEquals("Arista no existe", exception.getMessage());
        verify(edgeRepository, times(1)).existsById(1L);
        verify(edgeRepository, never()).deleteById(1L);
    }
}
