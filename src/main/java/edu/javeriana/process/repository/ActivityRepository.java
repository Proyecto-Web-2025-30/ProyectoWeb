package edu.javeriana.process.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.javeriana.process.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
