package edu.javeriana.process.service;

import edu.javeriana.process.model.Activity;
import edu.javeriana.process.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepo;

    @Transactional
    @Override
    public Activity create(Activity activity) {
        return activityRepo.save(activity);
    }

    @Transactional(readOnly = true)
    @Override
    public Activity getById(Long id) {
        return activityRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Activity> getAll() {
        return activityRepo.findAll();
    }

    @Transactional
    @Override
    public Activity update(Long id, Activity updated) {
        Activity existing = getById(id); // lanza excepción si no existe
        existing.setName(updated.getName());
        existing.setX(updated.getX());
        existing.setY(updated.getY());
        existing.setDescription(updated.getDescription());
        existing.setWidth(updated.getWidth());
        existing.setHeight(updated.getHeight());
        existing.setStatus(updated.getStatus());
        existing.setProcess(updated.getProcess());
        return activityRepo.save(existing);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!activityRepo.existsById(id)) {
            throw new IllegalArgumentException("Actividad no existe");
        }
        activityRepo.deleteById(id);
    }
}
