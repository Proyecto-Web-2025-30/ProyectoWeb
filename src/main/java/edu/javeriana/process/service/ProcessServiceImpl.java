package edu.javeriana.process.service;

import edu.javeriana.process.model.Process;
import edu.javeriana.process.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

    private final ProcessRepository processRepository;

    @Override
    public Process create(Process process) {
        return processRepository.save(process);
    }

    @Override
    public Process getById(Long id) {
        return processRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proceso no encontrado"));
    }

    @Override
    public List<Process> getAll() {
        return processRepository.findAll();
    }

    @Override
    public Process update(Long id, Process process) {
        Process existing = getById(id);
        existing.setName(process.getName());
        existing.setDescription(process.getDescription());
        existing.setStatus(process.getStatus());
        return processRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!processRepository.existsById(id)) {
            throw new IllegalArgumentException("Proceso no existe");
        }
        processRepository.deleteById(id);
    }
}
