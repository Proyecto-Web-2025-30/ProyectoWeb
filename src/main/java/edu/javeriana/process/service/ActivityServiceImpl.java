package edu.javeriana.process.service;

import edu.javeriana.process.DTOs.ActivityDTO;
import edu.javeriana.process.model.Activity;
import edu.javeriana.process.model.Process;
import edu.javeriana.process.repository.ActivityRepository;
import edu.javeriana.process.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepo;
    private final ProcessRepository processRepo;
    private final ModelMapper modelMapper;

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
        Activity existing = getById(id);
        modelMapper.map(updated, existing);
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

    @Override
    public ActivityDTO toDto(Activity activity) {
        return modelMapper.map(activity, ActivityDTO.class);
    }

    @Override
    public Activity toEntity(ActivityDTO dto) {
        Activity activity = modelMapper.map(dto, Activity.class);
        Process process = processRepo.findById(dto.getProcessId())
                .orElseThrow();
        activity.setProcess(process);
        return activity;
    }
}
