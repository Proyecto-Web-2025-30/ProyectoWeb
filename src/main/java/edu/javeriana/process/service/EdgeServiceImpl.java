package edu.javeriana.process.service;

import edu.javeriana.process.model.Edge;
import edu.javeriana.process.repository.EdgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EdgeServiceImpl implements EdgeService {

    private final EdgeRepository edgeRepo;

    @Transactional
    @Override
    public Edge create(Edge edge) {
        return edgeRepo.save(edge);
    }

    @Transactional(readOnly = true)
    @Override
    public Edge getById(Long id) {
        return edgeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Arista no encontrada"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Edge> getAll() {
        return edgeRepo.findAll();
    }

    @Transactional
    @Override
    public Edge update(Long id, Edge updated) {
        Edge existing = getById(id); // lanza excepción si no existe
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setType(updated.getType());
        return edgeRepo.save(existing);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!edgeRepo.existsById(id)) {
            throw new IllegalArgumentException("Arista no existe");
        }
        edgeRepo.deleteById(id);
    }
}
