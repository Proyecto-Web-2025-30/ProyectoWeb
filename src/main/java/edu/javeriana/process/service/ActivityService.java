package edu.javeriana.process.service;

import edu.javeriana.process.model.Activity;
import java.util.List;

public interface ActivityService {
    Activity create(Activity activity);         // CREATE
    Activity getById(Long id);                  // READ
    List<Activity> getAll();                    // READ all
    Activity update(Long id, Activity activity);// UPDATE
    void delete(Long id);                       // DELETE
}
