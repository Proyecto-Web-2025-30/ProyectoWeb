package edu.javeriana.process.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.javeriana.process.model.Edge;

public interface EdgeRepository extends JpaRepository<Edge, Long> {
}

