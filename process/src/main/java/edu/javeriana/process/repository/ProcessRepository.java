package edu.javeriana.process.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.javeriana.process.model.Process;

public interface ProcessRepository extends JpaRepository<Process, Long> {
}

